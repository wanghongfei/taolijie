package com.fh.taolijie.utils;

/**
 * 封装了程序用到的所有常量
 * Created by wanghongfei on 15-3-4.
 */
public class Constants {

    public static final String API_VERSION = "v1.1";
    public static final String SENDER_EMAIL = "wfc5582563@126.com";

    /**
     * 分隔path的定界符
     */
    public static final String DELIMITER = ";";
    /**
     * 默认一面显示8条结果
     */
    public static final int PAGE_CAPACITY = 8;

    public static final int TOKEN_LENGTH = 15;

    public static final long MAIL_VALID_MINUTES = 10;

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
        VERIFIED,
        /**
         * 认证失败
         */
        FAILED
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
        EMPLOYER
    }

    public static enum AccessAuthority {
        ME_ONLY,
        ALL
    }

    public enum ImageType {
        PNG,
        JPG,
        GIF,
        UNSUPPORTED
    }

    public static enum NotificationRange {
        /**
         * 只有目标member收到
         */
        PRIVATE,
        /**
         * 所有用户收到
         */
        GLOBAL,
        /**
         * 只有商家能收到
         */
        EMPLOYER,
        /**
         * 只有学生能收到
         */
        STUDENT,
    }

    public enum VerificationType {
        ID_FRONT, ID_BACK, ORG_FRONT, ORG_BACK, STU_ID_FRONT, STU_ID_BACK
    }

    public static enum MailType {
        ERROR,
        RESET_PASSWORD,
        FEEDBACK;

        @Override
        public String toString() {
            String str = null;
            switch (this) {
                case ERROR:
                    str = "服务器出错";
                    break;
                case FEEDBACK:
                    str = "用户反馈";
                    break;

                case RESET_PASSWORD:
                    str = "密码重置";
                    break;

                default:
                    str = "none";
            }

            return str;
        }
    }


    /**
     * 结算方式
     */
    public static enum WayToPay {
        WEEK,
        DAY,
        MONTH,
        OTHER;

        @Override
        public String toString() {
            switch (this) {
                case WEEK:
                    return "周结";
                case DAY:
                    return "周结";
                case MONTH:
                    return "月结";
                case OTHER:
                    return "面议";
                default:
                    throw new IllegalArgumentException("wrong WayToPay enum!");
            }
        }

        /*        public static final String WEEK = "周结";
        public static final String DAY = "日结";
        public static final String MONTH = "月结";
        public static final String OTHER = "面议";*/
    }


    public static class ApplicationRecord {
        public static final String KEY_ID = "id";
        public static final String KEY_TIME = "time";
        //public static final String KEY_ID = "id";
    }

    public static class FeedbackType {
        public static final String ADVICE = "产品建议";
        public static final String BUG = "程序BUG";
        //public static final String
    }

    public static class ErrorType{

        public static final String USERNAME_OR_PASSWORD_ERROR = "用户名密码错误";
        public static final String USER_INVALID_ERROR = "该用户已被封号";

        public static final String USERNAME_ILLEGAL = "用户名不合法";
        public static final String USERNAME_ERROR = "用户名错误";
        public static final String USERNAME_EXISTS = "用户名已存在";
        public static final String USERNAME_NOT_EXISTS = "用户名不存在";
        public static final String NOT_LOGGED_IN = "用户未登录";

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

        public static final String VERFIEDSUCCESS = "审核通过";
        public static final String VERFIEDFAILED = "审核未通过";

        public static final String ERROR = "出现错误";
        public static final String NOT_FOUND = "没有找到";
        public static final String PERMISSION_ERROR = "权限不足";


    }

}
