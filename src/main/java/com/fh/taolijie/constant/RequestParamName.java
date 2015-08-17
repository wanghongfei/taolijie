package com.fh.taolijie.constant;

/**
 * Created by whf on 8/17/15.
 */
public enum RequestParamName {
    APP_TOKEN;

    @Override
    public String toString() {
        switch (this) {
            case APP_TOKEN:
                return "appToken";

            default:
                throw new IllegalStateException();
        }
    }
}
