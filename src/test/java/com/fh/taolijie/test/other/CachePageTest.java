package com.fh.taolijie.test.other;

import com.fh.taolijie.utils.CachePageUtils;
import org.junit.Test;

/**
 * Created by wanghongfei on 15-4-3.
 */
public class CachePageTest {
    @Test
    public void testCacehPage() throws Exception {
        CachePageUtils.flush("http://localhost:8080/", "test.html");
    }
}
