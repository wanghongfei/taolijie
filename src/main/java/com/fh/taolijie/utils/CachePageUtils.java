package com.fh.taolijie.utils;

import java.io.*;

/**
 * Created by wanghongfei on 15-4-3.
 */
public class CachePageUtils {
    private static final int _1000 = 1000;

    private CachePageUtils() {}

    /**
     * 保存页面cache的目录
     */
    //public static String CACHED_PAGE_DIR = "/var";
    public static String CACHED_PAGE_DIR = "/home/whf/test";

    /**
     * 向指定{@code url}发GET请求，得到HTML代码，并保存在{@code fileName}中
     * @param url
     * @param fileName
     * @return 成功返回true,失败返回false
     * @throws IOException
     */
    public static boolean flush(String url, String fileName) throws IOException {
        StringBuilder cmd = new StringBuilder("wget");
        cmd.append(" ");
        cmd.append(url);
        cmd.append(" ");
        cmd.append("-O");
        cmd.append(" ");
        cmd.append(CACHED_PAGE_DIR);
        cmd.append(File.pathSeparator);
        cmd.append(fileName);

        Process process = Runtime.getRuntime().exec(cmd.toString());
        int exitCode = -1;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return exitCode == 0;
    }

    /**
     * 从文件中读取页面
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getFromCache(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(CACHED_PAGE_DIR + File.pathSeparator + fileName);
        BufferedInputStream bos = new BufferedInputStream(fis);

        InputStreamReader reader = new InputStreamReader(bos);
        char[] buf = new char[_1000];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        while ( (len = reader.read(buf)) != -1 ) {
            sb.append(buf, 0, len);
        }

        return sb.toString();
    }
}
