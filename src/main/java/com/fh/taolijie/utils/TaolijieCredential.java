package com.fh.taolijie.utils;

import cn.fh.security.credential.DefaultCredential;

/**
 * Created by wynfrith on 15-3-7.
 */
public class TaolijieCredential extends DefaultCredential{
//    public TaolijieCredential() {
//        super(null, username, nickName, credits);
//    }
    public TaolijieCredential(String username){
        super(null,username,null,null);
    }
    public TaolijieCredential(int id,String username){
        /*DefaultCredential类的构造器缺少id的赋值,暂时这样做*/
        super(null,username,null,null);
        super.id = id;
    }

}
