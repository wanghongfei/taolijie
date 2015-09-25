package com.fh.taolijie.utils;

import com.alibaba.fastjson.JSONObject;
import main.java.com.UpYun;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by whf on 9/25/15.
 */
public class UpYunUtils {
    private static char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    private static String DELIMITER = "&";

    private static String KEY = "nga5kPCby1v65FnEw+qC9VUzkAM=";
    //private static String KEY = "cAnyet74l9hdUag34h2dZu8z7gU=";


    private UpYunUtils() {}

    /**
     * 生成签名
     * @return
     */
    public static String sign(Map<String, Object> parmMap) {
        return sign(parmMap, KEY);
    }

    public static String sign(Map<String, Object> parmMap, String key) {
        // 将参数序列化为JSON
        JSONObject jsonO = new JSONObject();
        jsonO.putAll(parmMap);
        String json = jsonO.toJSONString();


        // 计算policy
        String policy = genPolicy(json).replaceAll("\n", "");
        // policy跟KEY组合
        String concat = StringUtils.concat(policy, DELIMITER, key);

        // 计算md5
        return md5(concat);
    }

    public static String genPolicy(String json) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(json.getBytes());
    }

    public static String md5(String s) {
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
