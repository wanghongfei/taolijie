package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.ImageModelMapper;
import com.fh.taolijie.domain.ImageModel;
import com.fh.taolijie.service.impl.DefaultImageResourceService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-17.
 */
@ContextConfiguration(classes = {
        DefaultImageResourceService.class
})
public class ImageServiceTest extends BaseSpringDataTestClass {
    @Autowired
    ImageModelMapper imgMapper;

    @Autowired
    DefaultImageResourceService service;

    @Test
    public void testAll() {
        ImageModel img = service.findImage(1);
        Assert.assertNotNull(img);

        ImageModel im = new ImageModel();
        im.setNewsId(1);
        service.saveImage(im);

        service.deleteImage(1);

        List<ImageModel> list = service.getInBatch(Arrays.asList(1, 2));
        Assert.assertFalse(list.isEmpty());

        list = service.getImageByJob(1);
        list = service.getImageByMember(1);
        list = service.getImageByNews(1);

    }
}
