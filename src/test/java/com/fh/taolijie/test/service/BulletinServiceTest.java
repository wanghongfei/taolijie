package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.BulletinDto;
import com.fh.taolijie.domain.BulletinEntity;
import com.fh.taolijie.service.BulletinService;
import com.fh.taolijie.service.impl.DefaultBulletinService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-31.
 */
@ContextConfiguration(classes = {
        DefaultBulletinService.class
})
public class BulletinServiceTest extends BaseSpringDataTestClass {
    @Autowired
    BulletinService bService;

    @PersistenceContext
    EntityManager em;

    BulletinEntity b1;

    @Before
    public void init() {
        b1 = new BulletinEntity();
        b1.setContent("content");
        b1.setCreatedTime(new Date());

        em.persist(b1);

        em.flush();
        em.clear();
    }

    @Test
    @Transactional(readOnly = false)
    public void testAdd() {
        BulletinDto dto = new BulletinDto();
        dto.setCreatedTime(new Date());
        dto.setContent("server will be down!");

        bService.addBulletin(dto);

        em.createQuery("SELECT b FROM BulletinEntity b WHERE b.content = :content", BulletinEntity.class)
                .setParameter("content", "server will be down!")
                .getSingleResult();
    }

    @Test
    @Transactional(readOnly = false)
    public void testDelete() {
        bService.deleteBulletin(this.b1.getId());

        try {
            em.createQuery("SELECT b FROM BulletinEntity b WHERE b.content = :content", BulletinEntity.class)
                    .setParameter("content", "content")
                    .getSingleResult();
        } catch (NoResultException ex) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetOne() {
        BulletinDto dto = bService.findOne(this.b1.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals("content", dto.getContent());
        Assert.assertNotNull(dto.getCreatedTime());
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetAll() {
        List<BulletinDto> dtoList = bService.getAll(0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());

        Assert.assertTrue(
                dtoList.stream().anyMatch( (dto) -> {
                    return dto.getContent().equals("content");
                })
        );
    }
}
