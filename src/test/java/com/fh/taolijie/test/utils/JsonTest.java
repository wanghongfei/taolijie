package com.fh.taolijie.test.utils;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wynfrith on 15-3-27.
 */
public class JsonTest {

    /**
     * 测试有数据的list
     */
    @Test
    public void testJsonStringList(){
        String expert = "[{\"password\":\"hhehehhe\",\"username\":\"hello\"}]";
        String actual = null;
        List<GeneralMemberDto> list = new ArrayList<>();
        GeneralMemberDto generalMemberDto = new GeneralMemberDto();
        generalMemberDto.setUsername("hello");
        generalMemberDto.setPassword("hhehehhe");
        list.add(generalMemberDto);

        actual = JSON.toJSONString(list);
        System.out.println(actual);

        Assert.assertEquals(expert,actual);

    }
    @Test
    public void testJsonStringList2(){
        List<JobPostDto> list = new ArrayList<>();
        JobPostDto jobPostDto = new JobPostDto();
        JobPostDto jobPostDto1 = new JobPostDto();
        jobPostDto1.setCategoryName("hello2");
        jobPostDto1.setTitle("标题2");
        jobPostDto.setCategoryName("hello");
        jobPostDto.setTitle("标题");
        list.add(jobPostDto);
        list.add(jobPostDto1);
        for(JobPostDto job:list)
        {
            System.out.println(job.getTitle());
            System.out.println(job.getCategoryName());
        }
        System.out.println(JSON.toJSONString(list));
    }

    /**
     * 无数据的list
     */
    @Test
    public void testJsonStringEmpty(){
        String expert = "[]";
        String actual = null;
        List<GeneralMemberDto> list = new ArrayList<>();
        actual = JSON.toJSONString(list);

        System.out.println(actual);

        Assert.assertEquals(expert,actual);

    }

    /**
     * 为null的List
     */
    @Test
    public void testJsonStringNull(){
        String expert = "null";
        String actual = null;
        List<GeneralMemberDto> list = null;
        actual = JSON.toJSONString(list);

        System.out.println(actual);

        Assert.assertEquals(expert,actual);

    }
}
