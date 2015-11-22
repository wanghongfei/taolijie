package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.constant.acc.WithdrawStatus;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.WithdrawApplyModelMapper;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.acc.WithdrawApplyModel;
import com.fh.taolijie.dto.OrderSignDto;
import com.fh.taolijie.dto.WeichatRespDto;
import com.fh.taolijie.exception.checked.acc.*;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.PayService;
import com.fh.taolijie.service.acc.WithdrawService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.*;

/**
 * 提现申请业务实现
 * Created by whf on 9/21/15.
 */
@Service
public class DefaultWithdrawService implements WithdrawService {
    private static final Logger log = LoggerFactory.getLogger(DefaultWithdrawService.class);

    @Autowired
    private WithdrawApplyModelMapper drawMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private CashAccModelMapper accMapper;

    @Autowired
    private CashAccService accService;

    @Autowired
    private PayService payService;

    public static final String PAY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    /**
     * 创建提现申请.
     * 0. 检查账户余额
     * 1. 设置冗余字段
     * 2. 设置状态为等待审核
     * 3. 插入记录
     *
     * @param model
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addWithdraw(WithdrawApplyModel model, String dealPwd, PayType payType)
            throws CashAccNotExistsException, BalanceNotEnoughException, InvalidDealPwdException, AccountNotSetException {

        // 查询钱包账户
        CashAccModel acc = accMapper.findByMemberId(model.getMemberId());
        if (null == acc) {
            throw new CashAccNotExistsException("");
        }

        // 创建提现申请model对象
        Date now = new Date();
        model.setUsername(acc.getUsername());
        model.setApplyTime(now);
        model.setUpdateTime(now);
        model.setAccId(acc.getId());
        model.setStatus(WithdrawStatus.WAIT_AUDIT.code());
        // 根据支付类型判断相关账号是否已经设置
        switch (payType) {
            case ALIPAY:
                if (null == acc.getAlipayAcc()) {
                    throw new AccountNotSetException();
                }
                model.setAlipayAcc(acc.getAlipayAcc());
                break;

            case WECHAT:
            {
                // 判断用户有没有绑定openid
                MemberModel mem = memMapper.selectByPrimaryKey(model.getMemberId());
                String openid = mem.getWechatToken();

                log.debug("openid = {}", openid);

                if (null == openid) {
                    throw new AccountNotSetException();
                }

                model.setOpenid(openid);
                break;
            }

            case BANK_ACC:
                if (null == acc.getBankAcc()) {
                    throw new AccountNotSetException();
                }
                model.setBankAcc(acc.getBankAcc());
                break;
        }

        // 验证交易密码
        if (!acc.getDealPassword().equals(dealPwd)) {
            throw new InvalidDealPwdException("");
        }

        // 检查账户余额
        BigDecimal balance = acc.getAvailableBalance();
        if (balance.compareTo(model.getAmount()) < 0) {
            // 余额不足
            throw new BalanceNotEnoughException("");
        }

        // 插入提现申请记录
        drawMapper.insertSelective(model);

        // 是微信支付, 则自动打款
        boolean reduceBalance = true;
        if (payType == PayType.WECHAT) {
            try {
                // 发起HTTPS请求
                boolean result = doWechatPayRequest(model.getAmount(), model.getId(), model.getOpenid());
                if (result) {
                    // 置提现状态为成功
                    model.setStatus(WithdrawStatus.DONE.code());
                } else {
                    // 置提现状态为失败
                    model.setStatus(WithdrawStatus.FAILED.code());
                    reduceBalance = false;
                }


            } catch (Exception e) {
                // 记录错误信息到日志
                LogUtils.logException(e);
                // 置提现状态为失败
                model.setStatus(WithdrawStatus.FAILED.code());
            }
        }

        // 减少账户余额
        if (reduceBalance) {
            accService.reduceAvailableMoney(acc.getId(), model.getAmount(), AccFlow.WITHDRAW);
        }


    }



    /**
     * 发起微信接口调用请求
     */
    private boolean doWechatPayRequest(BigDecimal amount, Integer orderId, String openid) throws Exception {
        boolean result = true;

        log.info("preparing issue POST request to Wechat: {}", PAY_URL);

        // 参数签名
        OrderSignDto sign = payService.sign(new HashMap<>(0), PayType.WECHAT);
        sign.amount = toCent(amount).toString();
        sign.desc = "微信提现";
        sign.partner_trade_no = orderId.toString();
        sign.openid = openid;

        // 创建HTTPS客户端对象
        CloseableHttpClient httpclient = initSSLClient(sign.mchid, sign.certiPath);

        try {


            // 将参数数据转换为XML字符串
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
            xStream.alias("xml", OrderSignDto.class);
            String xml = xStream.toXML(sign);

            log.info("xml = {}", xml);

            // 创建POST请求
            HttpPost httpPost = new HttpPost(PAY_URL);
            httpPost.setEntity(new StringEntity(xml));



            // 发起POST请求
            log.info("sending POST request");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                // 读取返回数据
                HttpEntity entity = response.getEntity();

                log.info("response status line: {}", response.getStatusLine());

                if (entity != null) {

                    // XML转换成对象
                    String respXML = StringUtils.stream2String(entity.getContent());
                    WeichatRespDto respDTO = xml2Dto(respXML);

                    // 检查return_code字段是否为成功
                    if (false == "SUCCESS".equals(respDTO.result_code)) {
                        LogUtils.getErrorLogger().error("wechat returned error message");
                        result = false;
                    }


                    log.info("response content:{}", respDTO);

                }

                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
            return result;
        }

    }

    /**
     * 将金额转换成分
     * @param amt
     * @return
     */
    private Integer toCent(BigDecimal amt) {
        return amt.multiply(BigDecimal.valueOf(100)).intValue();
    }

    /**
     * XML转换成DTO对象
     * @param xml
     * @return
     */
    private WeichatRespDto xml2Dto(String xml) {
        XStream xs = new XStream();
        xs.alias("xml", WeichatRespDto.class);
        return (WeichatRespDto) xs.fromXML(xml);
    }

    /**
     * 更新审核状态.
     *
     * @param withId
     * @param status 新状态
     * @param memo 审核备注
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean updateStatus(Integer withId, WithdrawStatus status, String memo)
            throws WithdrawNotExistsException, CashAccNotExistsException, BalanceNotEnoughException {

        WithdrawApplyModel model = drawMapper.selectByPrimaryKey(withId);
        if (null == model) {
            throw new WithdrawNotExistsException("");
        }

        if (status == WithdrawStatus.FAILED) {
            // 如果状态是失败
            // 则添加账户余额
            accService.addAvailableMoney(model.getAccId(), model.getAmount(), AccFlow.REFUND);
        }

        WithdrawApplyModel example = new WithdrawApplyModel();
        example.setId(withId);

        example.setMemo(memo);
        example.setStatus(status.code());
        example.setUpdateTime(new Date());

        return drawMapper.updateByPrimaryKeySelective(example) > 0 ? true : false;
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findAll(int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findAllByStatus(WithdrawStatus status, int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);
        example.setStatus(status.code());

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findByMember(Integer memId, int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);
        example.setMemberId(memId);

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findByMember(Integer memId, WithdrawStatus status, int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);
        example.setMemberId(memId);
        example.setStatus(status.code());

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public WithdrawApplyModel findById(Integer drawId) {
        return drawMapper.selectByPrimaryKey(drawId);
    }

    /**
     * 创建带证书的HTTP Client
     * @param mchid
     * @param certiPath
     * @return
     * @throws Exception
     */
    private CloseableHttpClient initSSLClient(String mchid, String certiPath) throws Exception {
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certiPath));
        try {
            keyStore.load(instream, mchid.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchid.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        return  HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

    }
}
