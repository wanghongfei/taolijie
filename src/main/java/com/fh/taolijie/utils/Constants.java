package com.fh.taolijie.utils;

/**
 * 封装了程序用到的所有常量
 * Created by wanghongfei on 15-3-4.
 */
public class Constants {
    /**
     * 数据库member表中verified字段的取值
     */
    public static enum VerifyStatus {
        /**
         * 未认证
         */
        NONE,
        /**
         * 正在认证
         */
        IN_PROCESS,
        /**
         * 已完成认证
         */
        VERIFIED
    }

    /**
     * 学校的类型
     */
    public static enum SchoolType {
        /**
         * 高中
         */
        HIGH_SCHOOL,
        /**
         * 本科
         */
        UNDERGRADUATE,
        /**
         * 专科
         */
        INSTITUTE
    }

    public static enum ApplicationConfig {
        DEBUG,
        PRODUCT
    }
}
