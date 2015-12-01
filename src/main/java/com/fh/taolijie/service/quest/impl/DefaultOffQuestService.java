package com.fh.taolijie.service.quest.impl;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.http.HttpClientFactory;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.constant.quest.OffQuestStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.OffCateModelMapper;
import com.fh.taolijie.dao.mapper.OffQuestModelMapper;
import com.fh.taolijie.domain.OffCateModel;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.dto.BaiduMapDto;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.HTTPConnectionException;
import com.fh.taolijie.exception.checked.PostIntervalException;
import com.fh.taolijie.exception.checked.certi.IdUnverifiedException;
import com.fh.taolijie.service.certi.IdCertiService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.service.quest.OffQuestService;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by whf on 11/30/15.
 * @author wanghongfei
 */
@Service
public class DefaultOffQuestService implements OffQuestService {
    private static Logger logger = LoggerFactory.getLogger(DefaultOffQuestService.class);

    @Autowired
    private OffQuestModelMapper questMapper;

    @Autowired
    private IdCertiService idService;

    @Autowired
    private IntervalCheckService intervalService;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private OffCateModelMapper cateMapper;

    @Autowired
    private JedisPool jedisPool;


    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void publish(OffQuestModel model) throws GeneralCheckedException {
        Integer memId = model.getMemId();

        // 发布时间间隔检查
        if (false == intervalService.checkOffQuestPostInterval(memId)) {
            throw new PostIntervalException();
        }

        // 检查用户是否完成了id认证
        boolean verified = idService.checkVerified(memId);
        if (false == verified) {
            throw new IdUnverifiedException();
        }


        // 执行发布
        model.setCreatedTime(new Date());
        // 设置username冗余字段
        String username = memMapper.selectUsername(memId);
        model.setUsername(username);
        // 设置cate_name冗余字段
        OffCateModel cate = cateMapper.selectByPrimaryKey(model.getCateId());
        model.setCateName(cate.getName());
        // 设置状态为已发布
        model.setStatus(OffQuestStatus.PUBLISHED.code());

        questMapper.insertSelective(model);
    }

    /**
     * 将任务经纬度发送到百度地图
     */
    private boolean sendPosition(BigDecimal longitude, BigDecimal latitude, Integer questId, String title) throws HTTPConnectionException {
        // 从redis中取出连接参数
        Map<String, String> map = JedisUtils.performOnce(jedisPool, jedis -> jedis.hgetAll(RedisKey.MAP_CONF.toString()) );
        String AK = map.get(RedisKey.MAP_AK.toString());
        String coord = map.get(RedisKey.MAP_COORD_TYPE.toString());
        String geotableId = map.get(RedisKey.MAP_GEOTABLE_ID.toString());
        String addr = map.get(RedisKey.MAP_CREATE_PT.toString());


        // 拼接GET请求参数
        Map<String, String> parmMap = new HashMap<>(15);
        parmMap.put("ak", AK);
        parmMap.put("coord_type", coord);
        parmMap.put("geotable_id", geotableId);
        parmMap.put("longitude", longitude.toString());
        parmMap.put("latitude", latitude.toString());
        parmMap.put("quest_id", questId.toString());
        parmMap.put("title", title);

        String queryStr = StringUtils.genUrlQueryString(parmMap);
        String finalUrl = StringUtils.concat(addr.length() + queryStr.length() + 1, "?", addr, queryStr);

        logger.info("sending GET request {}", finalUrl);

        // 发送请求
        CloseableHttpClient client = HttpClientFactory.getClient();
        HttpGet GET = new HttpGet(finalUrl);
        boolean result = true;
        try {
            // 实际发出请求
            CloseableHttpResponse resp = client.execute(GET, HttpClientContext.create());

            try {
                // 读取返回报文
                String data = StringUtils.stream2String(resp.getEntity().getContent());
                logger.info("response data:{}", data);

                // 转换成DTO对象
                BaiduMapDto dto = JSON.parseObject(data, BaiduMapDto.class);
                logger.debug("dto = {}", dto);

                // 判断结果是否成功
                if (false == "0".equals(dto.status)) {
                    // 失败
                    LogUtils.getErrorLogger().error("create point failed");
                    result = false;
                }

            } catch (IOException e) {

            } finally {
                resp.close();
            }

        } catch (IOException e) {
            // 发生连接错误
            // 记日志
            LogUtils.logException(e);
            throw new HTTPConnectionException();
        }

        return result;

    }

    @Override
    public ListResult<OffQuestModel> findBy(OffQuestModel cmd) {
        return null;
    }

    @Override
    public int distance(double start, double end) {
        return 0;
    }
}
