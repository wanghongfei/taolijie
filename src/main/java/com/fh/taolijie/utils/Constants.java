package com.fh.taolijie.utils;

/**
 * 封装了程序用到的所有常量
 * Created by wanghongfei on 15-3-4.
 */
public class Constants {
    public static final String SENDER_EMAIL = "wfc5582563@126.com";

    /**
     * 分隔path的定界符
     */
    public static final String DELIMITER = ";";
    /**
     * 默认一面显示8条结果
     */
    public static final int PAGE_CAPACITY = 8;

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
        PRODUCT,
    }

    public static enum RoleType {
        USER,
        ADMIN,
        STUDENT,
        BUSINESS
    }

    public static class ErrorType{

        public static final String USERNAME_OR_PASSWORD_ERROR = "用户名密码错误";

        public static final String USERNAME_ILLEGAL = "用户名不合法";
        public static final String USERNAME_ERROR = "用户名错误";
        public static final String USERNAME_EXISTS = "用户名已存在";
        public static final String USERNAME_NOT_EXISTS = "用户名不存在";

        public static final String PASSWORD_ILLEGAL = "密码不合法";
        public static final String PASSWORD_ERROR = "密码错误";

        public static final String REPASSWORD_ERROR = "密码不一致";

        public static final String EMAIL_ILLEGAL = "邮箱不合法";
        public static final String EMAIL_ERROR = "邮箱错误";

        public static final String NOT_EMPTY = "字段不为空";

        public static final String SUCCESS = "操作成功";
        public static final String FAILED = "操作失败";
        public static final String DELETE_FAILED = "删除失败";
        public static final String PARAM_ILLEGAL = "参数不合法";

        public static final String CANT_DELETE_CURRENT_USER = "不能删除当前用户";


    }

}
