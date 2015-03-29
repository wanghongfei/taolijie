package com.fh.taolijie.utils;

/**
 * Created by wynfrith on 15-3-29.
 */
/*注册随机生成头像*/
public class DefaultAvatarGenerator {

    public static String getRandomAvatar(){
        return "/assets/images/defaultAvatars/Fruit-"+((int)(Math.random()*20)+1)+".png";
    }
}
