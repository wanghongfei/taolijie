package com.fh.taolijie.test.other;

import com.fh.taolijie.component.LogWrapper;
import org.junit.Test;
import org.slf4j.LoggerFactory;


/**
 * Created by whf on 11/29/15.
 */
public class LoggerTest {
    @Test
    public void testDebugLambda() {
        LogWrapper log = new LogWrapper(LoggerFactory.getLogger(LoggerTest.class));
        log.debug( () -> new String[] {"hello, {}", "wanghongfei"});

        log.debug( () -> new String[] {"hello, {}, this is {}", "wanghongfei", "bob"});
    }
}
