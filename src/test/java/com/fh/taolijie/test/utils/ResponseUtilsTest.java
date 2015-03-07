package com.fh.taolijie.test.utils;

import com.fh.taolijie.utils.ResponseUtils;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wanghongfei on 15-3-4.
 */

@RunWith(MockitoJUnitRunner.class)
public class ResponseUtilsTest {
    @Mock
    HttpServletRequest mockRequest;

    @Test
    public void testDeterminePage4Mobile() {
        // for android
        Mockito.when(mockRequest.getHeader("User-Agent"))
                .thenReturn("Mozilla/5.0 (Linux; U; Android 2.2.1; de-de; X2 Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");

        String jspPage = "home";
        String resultPage = ResponseUtils.determinePage(mockRequest, jspPage);
        Assert.assertEquals("mobile/home", resultPage);

        // for iPad
        Mockito.when(mockRequest.getHeader("User-Agent"))
                .thenReturn("Mozilla/5.0 (iPad; CPU OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B329 Safari/8536.25");
        resultPage = ResponseUtils.determinePage(mockRequest, jspPage);
        Assert.assertEquals("mobile/home", resultPage);

        // for iphone
        Mockito.when(mockRequest.getHeader("Use-Agent"))
                .thenReturn("Mozilla/5.0 (iPod; CPU iPhone OS 5_1_1 like Mac OS X; nl-nl) AppleWebKit/534.46.0 (KHTML, like Gecko) CriOS/21.0.1180.80 Mobile/9B206 Safari/7534.48.3");
        resultPage = ResponseUtils.determinePage(mockRequest, jspPage);
        Assert.assertEquals("mobile/home", resultPage);

    }

    @Test
    public void testDeterminePage4PC() {
        Mockito.when(mockRequest.getHeader("User-Agent"))
                .thenReturn("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");

        String jspPage = "home";
        String resultPage = ResponseUtils.determinePage(mockRequest, jspPage);
        Assert.assertEquals("pc/home", resultPage);
    }

    @Test
    public void testDeterminePage4Null(){
        Mockito.when(mockRequest.getHeader("User-Agent"))
                .thenReturn(null);

        String jspPage = "home";
        String resultPage = ResponseUtils.determinePage(mockRequest, jspPage);
        Assert.assertEquals("pc/home", resultPage);
    }
}
