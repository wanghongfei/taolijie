package com.fh.taolijie.utils;

import cn.fh.security.credential.DefaultCredential;

/**
 * Created by wynfrith on 15-3-7.
 */
public class TaolijieCredential extends DefaultCredential{
    public TaolijieCredential(String username, String nickName, Integer credits) {
        super(null, username, nickName, credits);
    }
    public TaolijieCredential(String username){
        super(null,username,null,null);
    }

}
