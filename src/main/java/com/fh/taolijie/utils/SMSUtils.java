package com.fh.taolijie.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.fh.taolijie.component.SMSResponse;
import com.fh.taolijie.component.http.HttpClientFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信发送工具类
 * Created by whf on 10/8/15.
 */
public class SMSUtils {
    private static final Logger infoLog = LogUtils.getInfoLogger();

    private static final String SMS_URL = "http://api.weimi.cc/2/sms/send.html";
    private static final String SMS_UID = "Ctpc2fjQKMq3";
    private static final String SMS_PAS = "7yaz2zeb";
    private static final String SMS_CID = "vJWD1eXBm0AM";


    private SMSUtils() {}

    /**
     * 向指定手机号发送短信验证码
     * @param code
     * @param mobs
     * @return
     */
    public static boolean sendCode(String code, String... mobs) {
        try {
            HttpClient client = HttpClientFactory.getClient();
            HttpPost post = new HttpPost(SMS_URL);

            List<NameValuePair> parmList = new ArrayList<>(6);
            parmList.add(new BasicNameValuePair("uid", SMS_UID));
            parmList.add(new BasicNameValuePair("pas", SMS_PAS));
            parmList.add(new BasicNameValuePair("cid", SMS_CID));
            parmList.add(new BasicNameValuePair("p1", code));
            parmList.add(new BasicNameValuePair("type", "json"));

            String mobList = genMobList(mobs);
            if (infoLog.isDebugEnabled()) {
                infoLog.debug("generate mobile list:{}", mobList);
            }

            infoLog.info("[phone]: sending SMS to {}", mobList);

            parmList.add(new BasicNameValuePair("mob", mobList));

            post.setEntity(new UrlEncodedFormEntity(parmList, HTTP.UTF_8));

            // 发送请求
            HttpResponse resp = client.execute(post);
            String str = StringUtils.stream2String(resp.getEntity().getContent());
            infoLog.info("[phone]: response received for {}:", mobList, str);
            if (infoLog.isDebugEnabled()) {
                infoLog.debug("SMS response received: {}, content = {}", resp.getStatusLine().getStatusCode(), str);
            }

            try {
                SMSResponse smsResponse = JSON.parseObject(str, SMSResponse.class);
                Integer respCode = smsResponse.getCode();
                if (null == respCode || respCode.intValue() != 0) {
                    LogUtils.getErrorLogger().error("SMS sending failed! ");
                    return false;
                }

            } catch (JSONException ex) {
                LogUtils.logException(ex);
                return false;
            }


        } catch (IOException ex) {
            LogUtils.logException(ex);
            return false;
        }

        return true;
    }

    private static String genMobList(String... mobs) {
        StringBuilder sb = new StringBuilder(15);

        for (String mob : mobs) {
            sb.append(mob);
            sb.append(",");
        }

        // 删除最后的","
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
