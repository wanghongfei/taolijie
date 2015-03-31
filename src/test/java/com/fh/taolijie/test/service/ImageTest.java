package com.fh.taolijie.test.service;

import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * Created by wanghongfei on 15-3-31.
 */
public class ImageTest extends BaseSpringDataTestClass {
    @PersistenceContext
    EntityManager em;

    private int _1M = 1024 * 1024;

    @Test
    @Transactional(readOnly = false)
    public void test2MSize() throws IOException {
        /*FileInputStream fis = new FileInputStream("/home/whf/Pictures/1-2M.jpg");
        BufferedInputStream bis = new BufferedInputStream(fis);

        byte[] buf = new byte[_1M * 2];
        int len = bis.read(buf);
        System.out.println("IO finished. size = " + len);

        ImageResourceEntity image = new ImageResourceEntity();
        image.setBinData(buf);

        em.persist(image);
        em.flush();*/
    }
}
