package com.fh.taolijie.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by wanghongfei on 15-3-30.
 */
public class StringUtils {
    private static final String objAlias = "obj";

    private StringUtils() {
    }

    /**
     * 随机生成一个{@code length}位的字符串
     * @param length
     * @return
     */
    public static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }


    /**
     * 构造 {@code SELECT XXX FROM XXX WHERE XXX LIKE '%XXX%' AND XXX ORDER By XXX}语句
     * @param entityName
     * @param likeParam
     * @param orderBy
     * @return
     */
    public static String buildLikeQuery(String entityName, Map<String, Object> likeParam, Map.Entry<String, String> orderBy) {
        StringBuilder query = new StringBuilder();

        // 构造SELECT语句
        query.append("SELECT").append(" ");
        query.append(objAlias).append(" ");
        query.append("FROM").append(" ");
        query.append(entityName).append(" ");
        query.append(objAlias).append(" ");

        // 构造WHERE语句
        if (false == likeParam.isEmpty()) {
            query.append("WHERE").append(" ");

            Set<Map.Entry<String, Object>> entrySet = likeParam.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                query.append(objAlias).append(".").append(entry.getKey()).append(" ").append("LIKE")
                        .append(" ").append("CONCAT('%', :").append(entry.getKey()).append(", '%' )");
                query.append(" ");
                query.append("AND");
                query.append(" ");
            }

            // 去掉最后的AND
            int len = query.length();
            String ql = query.substring(0, len - 4);
            query = new StringBuilder(ql);

        }

        // 构造ORDER BY
        if (null != orderBy) {
            query.append("ORDER BY").append(" ");
            query.append(objAlias).append(".").append(orderBy.getKey()).append(" ").append(orderBy.getValue());
        }

        return query.toString();
    }

    /**
     * 构造 {@code SELECT XXX FROM XXX WHERE XXX = 'XXX' AND XXX ORDER By XXX}语句
     * @param objAlias
     * @param entityName
     * @param whereParm
     * @param orderBy
     * @return
     */
    public static String buildQuery(String objAlias, String entityName, Map<String, Object> whereParm, Map.Entry<String, String> orderBy) {
        StringBuilder query = new StringBuilder();

        // 构造SELECT语句
        query.append("SELECT").append(" ");
        query.append(objAlias).append(" ");
        query.append("FROM").append(" ");
        query.append(entityName).append(" ");
        query.append(objAlias).append(" ");

        // 构造WHERE语句
        if (false == whereParm.isEmpty()) {
            query.append("WHERE").append(" ");

            Set<Map.Entry<String, Object>> entrySet = whereParm.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                query.append(objAlias).append(".").append(entry.getKey()).append("=").append(":").append(entry.getKey());
                query.append(" ");
                query.append("AND");
                query.append(" ");
            }

            // 去掉最后的AND
            int len = query.length();
            String ql = query.substring(0, len - 4);
            query = new StringBuilder(ql);

        }

        // 构造ORDER BY
        if (null != orderBy) {
            query.append("ORDER BY").append(" ");
            query.append(objAlias).append(".").append(orderBy.getKey()).append(" ").append(orderBy.getValue());
        }

        return query.toString();
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

    public static boolean checkIdExists(String idsString, String idString) {
        if (null == idsString) {
            return false;
        }

        String[] ids = idString.split(Constants.DELIMITER);

        return Arrays.stream(ids)
                .anyMatch( (id) -> id.equals(idString) );
    }
}
