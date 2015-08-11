package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.domain.ApplicationIntendModel;
import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.impl.DefaultResumeService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-16.
 */

@ContextConfiguration( classes = {
        DefaultResumeService.class
})
public class ResumeServiceTest extends BaseSpringDataTestClass {
    @Autowired
    ResumeService service;

    @Test
    public void testGet() {
    }

    @Test
    public void testUpdate() {
        ResumeModel model = new ResumeModel();
        model.setId(1);
        model.setAge(12);
        service.updateResume(1, model);

        service.refresh(model.getId());
    }

    @Test
    public void testFavorite() {
        service.favoriteResume(1, 1);
        Assert.assertTrue(service.isAlreadyFavorite(1, 1));
        service.unFavorite(1, 1);
    }

    @Test
    public void testAddResume() {
        ResumeModel model = new ResumeModel();
        model.setAge(100);
        service.addResume(model);
    }

    @Test
    public void testAddIntend() {
        ApplicationIntendModel intendModel = new ApplicationIntendModel();
        intendModel.setJobPostCategoryId(1);
        intendModel.setResumeId(1);
        service.addIntend(intendModel);
    }

    @Test
    public void testDeleteIntend() {
        ApplicationIntendModel intendModel = new ApplicationIntendModel();
        intendModel.setJobPostCategoryId(1);
        intendModel.setResumeId(1);
        service.addIntend(intendModel);
        service.deleteIntend(intendModel);

    }

    @Test
    public void testFindResume() {
        ResumeModel model = service.findResume(1);
        Assert.assertNotNull(model);
    }

    @Test
    public void testDeleteResume() {
        // 级联删除失败
        //service.deleteResume(1);
    }
}
