package com.fh.taolijie.test.other;

import cn.fh.security.utils.CredentialUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wynfrith on 15-3-7.
 */
public class PasswordSecurityTest {

    @Test
    public void testSha1(){

        System.out.println(CredentialUtils.sha("wfc5582563"));
    }

    @Test
    public void testString(){
        System.out.println("!hello".substring(1));

        Assert.assertTrue("hello"=="he"+"llo");
    }

}

