package com.fh.taolijie.utils;

/**
 * Created by wanghongfei on 15-3-30.
 */
public class StringUtils {
    private StringUtils() {

    }

    /**
     * "apple" --> apple
     * @param str
     * @return
     */
    public static String trimQuotation(String str) {
        return str.substring(1, str.length() - 1);
    }


    public static String addToString(String originalStr, String newStr) {
        StringBuilder sb = new StringBuilder();
        if (null == originalStr) {
            sb.append(newStr);
            sb.append(Constants.DELIMITER);
        } else {
            sb.append(originalStr);
            sb.append(newStr);
            sb.append(Constants.DELIMITER);
        }

        return sb.toString();
    }
}
