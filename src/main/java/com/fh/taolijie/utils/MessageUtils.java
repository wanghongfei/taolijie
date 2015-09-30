package com.fh.taolijie.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.fh.taolijie.cache.message.model.MsgProtocol;
import com.fh.taolijie.exception.checked.InvalidMessageException;

/**
 * Created by whf on 9/30/15.
 */
public class MessageUtils {
    private MessageUtils() {}

    /**
     * 解码消息
     * @param buf
     * @return
     * @throws InvalidMessageException
     */
    public static MsgProtocol deserailize(byte[] buf) throws InvalidMessageException {
        try {
            return JSON.parseObject(buf, MsgProtocol.class);

        } catch (JSONException e) {
            throw new InvalidMessageException();
        }
    }
}
