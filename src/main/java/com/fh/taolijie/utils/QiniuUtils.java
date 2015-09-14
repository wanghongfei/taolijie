package com.fh.taolijie.utils;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.URLUtils;
import org.apache.commons.codec.EncoderException;
import org.json.JSONException;

import java.io.File;

/**
 * 7牛云工具类
 * Created by whf on 9/14/15.
 */
public class QiniuUtils {
    public static final String ACCESS_KEY = "jiCcIJ426S8uHSN4fqL5OGjEmqSdENWTnk4Ze8I6";
    public static final String SECRET_KEY = "rshYBtwtUWYCG5mcsFsHBnhz1vrgpaLKDymeNqiB";

    public static final String SPACE_NAME = "taolijie";

    public static Mac mac;

    static {
        mac = new Mac(ACCESS_KEY, SECRET_KEY);
    }

    /**
     * @deprecated
     */
    public static void upload(String key) throws Exception {
        PutPolicy putPolicy = new PutPolicy(SPACE_NAME);
        String uptoken = putPolicy.token(mac);

        PutExtra extra = new PutExtra();
        String localFile = "/Users/whf/Pictures/4201347.jpeg";
        PutRet ret = IoApi.putFile(uptoken, key, new File(localFile), extra);


        System.out.println(ret);
    }

    /**
     * 为资源生成URL
     * @param domain
     * @param key
     * @return
     * @throws EncoderException
     * @throws AuthException
     */
    public static String genUrl(String domain, String key) throws EncoderException, AuthException {
        String baseUrl = URLUtils.makeBaseUrl(domain, key);
        GetPolicy getPolicy = new GetPolicy();
        return getPolicy.makeRequest(baseUrl, mac);
    }

    /**
     * 生成上传token
     * @param key
     * @return
     * @throws AuthException
     * @throws JSONException
     */
    public static String genToken(String key) throws AuthException, JSONException {
        PutPolicy putPolicy = new PutPolicy(SPACE_NAME);
        putPolicy.scope = StringUtils.concat(SPACE_NAME, ":", key);
        return putPolicy.token(mac);
    }
}
