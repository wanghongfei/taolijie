package com.fh.taolijie.constant;

/**
 * Created by whf on 8/17/15.
 */
public enum RequestParamName {
    APP_TOKEN,
    HEADER_APP_TOKEN,
    WECHAT_TOKEN;

    @Override
    public String toString() {
        switch (this) {
            case APP_TOKEN:
                return "appToken";

            case WECHAT_TOKEN:
                return "wechat";

            case HEADER_APP_TOKEN:
                return "App-Token";

            default:
                throw new IllegalStateException();
        }
    }
}
