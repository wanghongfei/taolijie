package com.fh.taolijie.constant;

/**
 * Created by whf on 8/2/15.
 */
public enum OperationType {
    // 发布信息
    POST,
    FAKE_POST, // 发虚假信息
    REFRESH, // 刷新

    REGISTER, // 注册新用户
    LOGIN, //每日登陆

    FAV, //收藏信息

    LIKE, // 被点赞
    COMPLAINT // 被举报
}
