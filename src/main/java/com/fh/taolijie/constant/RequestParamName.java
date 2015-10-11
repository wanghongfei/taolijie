package com.fh.taolijie.constant;

/**
 * Created by whf on 8/17/15.
 */
public enum RequestParamName {
    APP_TOKEN,
    WECHAT_TOKEN;

    @Override
    public String toString() {
        switch (this) {
            case APP_TOKEN:
                return "appToken";

            case WECHAT_TOKEN:
                return "wechat";

            default:
                throw new IllegalStateException();
        }
    }
}
