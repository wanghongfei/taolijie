package com.fh.taolijie.utils;

import java.io.IOException;

/**
 * Created by wanghongfei on 15-4-3.
 */
public class CachePageUtils {
    private CachePageUtils() {}

    /**
     * 保存页面cache的目录
     */
    //public static String CACHED_PAGE_DIR = "/var";
    public static String CACHED_PAGE_DIR = "/home/whf/test";

    public static boolean flush(String url) throws IOException {
        StringBuilder cmd = new StringBuilder("wget");
        cmd.append(" ");
        cmd.append(url);
        cmd.append(" ");
        cmd.append("-O");
        cmd.append(" ");
        cmd.append(CACHED_PAGE_DIR);

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
}
