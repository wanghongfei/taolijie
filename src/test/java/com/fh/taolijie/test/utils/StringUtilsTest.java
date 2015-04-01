package com.fh.taolijie.test.utils;

import com.fh.taolijie.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghongfei on 15-3-31.
 */
public class StringUtilsTest {
    @Test
    public void testBuildJPQL() {
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("name", "wanghongfei");
        parmMap.put("age", "20");
        parmMap.put("home", "none");

        Map.Entry<String, String> order = new AbstractMap.SimpleEntry<String, String>("time", "DESC");

        String query = StringUtils.buildQuery("job", "JobPostEntity", parmMap, order);

        Assert.assertEquals("SELECT job FROM JobPostEntity job WHERE job.name=:name AND job.age=:age AND job.home=:home ORDER BY job.time DESC", query);
    }

    @Test
    public void testLikeJPQL() {
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("name", "wanghongfei");
        parmMap.put("age", "20");
        parmMap.put("home", "none");

        Map.Entry<String, String> order = new AbstractMap.SimpleEntry<String, String>("time", "DESC");

        String query = StringUtils.buildLikeQuery("JobPostEntity", parmMap, order);
        System.out.println(query);

        //Assert.assertEquals("SELECT job FROM JobPostEntity job WHERE job.name=:name AND job.age=:age AND job.home=:home ORDER BY job.time DESC", query);

    }
}
