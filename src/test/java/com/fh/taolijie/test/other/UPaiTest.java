package com.fh.taolijie.test.other;

import com.fh.taolijie.utils.UpYunUtils;
import com.qiniu.util.Auth;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 9/25/15.
 */
public class UPaiTest {
    @Test
    public void testSign() {
        Map<String, Object> parm = new HashMap<>();
        parm.put("bucket", "demobucket");
        parm.put("expiration", Integer.valueOf(1409200758) );
        parm.put("save-key", "/img.jpg");


        String sign = UpYunUtils.sign(parm, "cAnyet74l9hdUag34h2dZu8z7gU=");
        Assert.assertEquals("646a6a629c344ce0e6a10cadd49756d4", sign);
    }
}
