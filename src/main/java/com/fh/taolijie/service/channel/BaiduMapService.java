package com.fh.taolijie.service.channel;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.http.HttpClientFactory;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.dto.map.BaiduMapNearbyRespDto;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.HTTPConnectionException;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by whf on 12/2/15.
 */
@Service
public class BaiduMapService {
    private static Logger logger = LoggerFactory.getLogger(BaiduMapService.class);


    /**
     * 调用百度地图接口
     * @param url 接口地址
     * @param paramMap 参数列表
     * @param method 请求方法
     * @param retType 返回值类型
     * @param <RETURN> 返回类型
     * @return
     * @throws GeneralCheckedException
     */
    public <RETURN> RETURN sendRequest(String url, Map<String, String> paramMap, RequestMethod method, Class<RETURN> retType) throws GeneralCheckedException {
        logger.debug("parameter = {}", paramMap);


        HttpUriRequest request = null;

        // 拼接GET请求
        if (RequestMethod.GET == method) {
            // 构造GET请求参数
            String queryStr = StringUtils.genUrlQueryString(paramMap);
            String finalUrl = StringUtils.concat(queryStr.length() + url.length() + 5, url, "?", queryStr);

            request = new HttpGet(finalUrl);

            logger.info("sending request to {}", finalUrl);

        } else if (RequestMethod.POST == method) {
            // 构造POST请求参数
            List<NameValuePair> parmList = new ArrayList<>(paramMap.size());
            paramMap.entrySet().forEach( entry -> {
                parmList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            });

            HttpPost POST = new HttpPost(url);

            try {
                POST.setEntity(new UrlEncodedFormEntity(parmList));

            } catch (UnsupportedEncodingException e) {
                LogUtils.logException(e);
                throw new HTTPConnectionException();
            }

            request = POST;

        } else {
            throw new IllegalStateException("method " + method.name() + " illegal");
        }


        // 创建httpclient
        CloseableHttpClient client = HttpClientFactory.getClient();

        // 发送请求
        // 返回值
        RETURN ret = null;
        try {
            CloseableHttpResponse resp = client.execute(request, HttpClientContext.create());

            try {
                // 读取返回报文
                String data = StringUtils.stream2String(resp.getEntity().getContent());
                logger.info("response data = {}", data);

                // to DTO
                ret = JSON.parseObject(data, retType);
                logger.debug("dto = {}", ret);


            } catch (Exception e) {
                LogUtils.logException(e);
                throw new HTTPConnectionException();

            } finally {
                resp.close();
            }

        } catch (IOException e) {
            LogUtils.logException(e);
            throw new HTTPConnectionException();
        }

        return ret;
    }
}
