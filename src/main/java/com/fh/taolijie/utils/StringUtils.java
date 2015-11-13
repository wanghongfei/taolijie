package com.fh.taolijie.utils;

import com.fh.taolijie.exception.checked.InvalidNumberStringException;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

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
     * 将以分号分隔的id字符串转换成id List
     * @param intendIds
     * @return
     * @throws InvalidNumberStringException
     */
    public static List<Integer> splitIntendIds(String intendIds) throws InvalidNumberStringException {
        String[] ids = intendIds.split(Constants.DELIMITER);
        if (null == ids || 0 == ids.length) {
            throw new InvalidNumberStringException("intendIds格式错误");
        }

        List<Integer> idList = new ArrayList<>(10);
        try {
            for (String idStr : ids) {
                Integer id = Integer.valueOf(idStr);
                idList.add(id);
            }

        } catch (NumberFormatException ex) {
            throw new InvalidNumberStringException("intendIds参数非数字");
        }

        return idList;
    }

    public static String stream2String(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in);

        char[] cbuf = new char[100];
        int len = -1;

        StringBuilder sb = new StringBuilder();
        while ( (len = reader.read(cbuf)) != -1 ) {
            sb.append(cbuf, 0, len);
        }

        return sb.toString();
    }
    /**
     * @deprecated
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
     * @deprecated
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

    /**
     * 从一个id组成的字符串中去掉指定id.
     * 如，将 "1;2;3;4;5;" 去掉 "2" 后的结果为 "1;3;4;5;".
     * 如果{@code originalStr}中不包含{@code strToBeRemoved}，则直接返回原字符串
     * @param originalStr
     * @param strToBeRemoved
     * @return
     */
    public static String removeFromString(String originalStr, String strToBeRemoved) {
        if (null == originalStr) {
            throw new IllegalArgumentException("original string 不能为null");
        }

        String fullString = strToBeRemoved + Constants.DELIMITER;
        StringBuilder sb = new StringBuilder(originalStr);

        int pos = sb.indexOf(fullString);
        // 原字符串中不包含要删除的内容
        // 直接返回原串
        if (-1 == pos) {
            return originalStr;
        }
        sb.replace(pos, pos + fullString.length(), "");

        return sb.toString();
    }

    /**
     * 验证传入的参数是否符合: if strs[i] exists then strs[i+1] must exist
     * @param strs
     * @return
     */
    public static boolean validateLadderString(String... strs) {
        if (null == strs) {
            return true;
        }

        final int LEN = strs.length;
        if (LEN <= 1) {
            return true;
        }

        for (int ix = 0 ; ix < LEN ; ++ix) {
            if (null != strs[ix]) {
                // 找到第一个不为null的元素
                // 判断从该元素开始后面的元素是不是都不为空
                for (int j = ix ; j < LEN ; ++j) {
                    if (null == strs[j]) {
                        return false;
                    }
                }

                break;
            }
        }

        return true;
    }

    /**
     * 生成请求参数字符串
     * @param map
     * @return
     */
    public static String genUrlQueryString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder(map.size() * 20);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * 得到URL中最后一个'/'后面的字符串. 如/api/user/12结果为12
     * @param url
     * @return
     */
    public static String getLastToken(String url) {
        if (null == url) {
            return null;
        }

        int pos = url.lastIndexOf('/');

        return url.substring(pos + 1);
    }

    /**
     * 检查字符串的每个字符是不是数字字符
     * @param str
     * @return
     */
    public static boolean isAllDigitChar(String str) {
        if (null == str) {
            return false;
        }

        final int LEN = str.length();
        for (int ix = 0 ; ix < LEN ; ++ix ) {
            if ( false == Character.isDigit(str.charAt(ix)) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * 在字符串前后加上双引号
     * @param str
     * @return
     */
    public static String surroundQuotation(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 2);

        sb.append("\"");
        sb.append(str);
        sb.append("\"");

        return sb.toString();
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

    public static boolean isStaticResource(String url) {
        return url.startsWith("/admin")
                || url.startsWith("/images")
                || url.startsWith("/scripts")
                || url.startsWith("/styles")
                || url.startsWith("/fonts")
                || url.startsWith("/static")
                || url.startsWith("/about");
    }

    public static String[] splitIds(String ids) {
        if (null == ids) {
            return null;
        }

        return ids.split(Constants.DELIMITER);
    }

    public static List<Integer> toIdList(String idString) {
        String[] ids = splitIds(idString);
        if (null == ids) {
            return null;
        }

        return Arrays.stream(ids)
                .map((idStr) -> Integer.valueOf(idStr))
                .collect(Collectors.toList());
    }

    public static boolean checkIdExists(String[] ids, String targetId) {
        if (null == ids) {
            return false;
        }

        return Arrays.stream(ids)
                .anyMatch((id) -> id.equals(targetId));
    }

    public static boolean checkIdExists(String idsString, String idString) {
        if (null == idsString) {
            return false;
        }

        String[] ids = idsString.split(Constants.DELIMITER);

        return Arrays.stream(ids)
                .anyMatch( (id) -> id.equals(idString) );
    }

    public static boolean checkEqualAndNotEmpty(String str1, String str2) {
        if (null == str1 || null == str2) {
            return false;
        }

        if (str1.isEmpty() || str2.isEmpty()) {
            return false;
        }

        return str1.equals(str2);
    }

    /**
     * 是空串返回false
     * @param str
     * @return
     */
    public static boolean checkNotEmpty(String str) {
        if (null == str || str.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 检查字符串是否全部非空
     * @param strs
     * @return
     */
    public static boolean checkAllNotEmpty(String... strs) {
        for (String str : strs) {
            if (null == str || str.isEmpty()) {
                return false;
            }
        }

        return true;
    }


    /**
     * 字符串拼接
     * @param capacity 可以指定最终的字符串长度, 提高性能
     * @param objs
     * @return
     */
    public static String concat(int capacity, Object... objs) {
        if (capacity <= 0) {
            capacity = 30;
        }

        StringBuilder sb = new StringBuilder(capacity);

        Arrays.stream(objs).forEach( o -> sb.append(o) );

        return sb.toString();
    }

    /**
     * 将整数List转换成以分号分隔的字符串
     * @param idList
     * @return
     */
    public static String listToString(List<Integer> idList) {
        StringBuilder sb = new StringBuilder(20);
        idList.forEach( id -> {
            sb.append(id);
            sb.append(Constants.DELIMITER);
        });

        return sb.toString();
    }
}
