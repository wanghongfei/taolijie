package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.service.impl.DefaultJobPostCategoryService;
import com.fh.taolijie.test.BaseDatabaseTestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-6.
 */
@ContextConfiguration(classes = {DefaultJobPostCategoryService.class})
public class JobPostCategoryServiceTest extends BaseDatabaseTestClass {
    private JobPostCategoryEntity cate1;
    private JobPostCategoryEntity cate2;

    @Autowired
    private DefaultJobPostCategoryService cateService;

    @PersistenceContext
    private EntityManager em;

    @Before
    @Transactional(readOnly = false)
    public void initData() {
        // 创建2个分类
        cate1 = new JobPostCategoryEntity();
        cate1.setName("dog");
        em.persist(cate1);

        cate2 = new JobPostCategoryEntity();
        cate2.setName("cat");
        em.persist(cate2);
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetList() {
        List<JobPostCategoryDto> dtoList = cateService.getCategoryList(0, 0);
        boolean contains = containsCategory(dtoList, "dog");
        Assert.assertTrue(contains);
        contains = containsCategory(dtoList, "cat");
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteCate() throws CategoryNotEmptyException {
        cateService.deleteCategory(this.cate1.getId());

        try {
            em.createQuery("SELECT c FROM JobPostCategoryEntity  c WHERE c.name = :name", JobPostCategoryEntity.class)
                    .setParameter("name", "dog")
                    .getSingleResult();
        } catch (NoResultException e) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdate() {
        JobPostCategoryDto dto = new JobPostCategoryDto();
        dto.setName("fish");

        cateService.updateCategory(this.cate1.getId(), dto);


        em.createQuery("SELECT c FROM JobPostCategoryEntity  c WHERE c.name = :name", JobPostCategoryEntity.class)
                .setParameter("name", "fish")
                .getSingleResult();
    }

    @Test
    public void testFindCategory() {
        JobPostCategoryDto dto = cateService.findCategory(this.cate1.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals("dog", dto.getName());
    }

    private boolean containsCategory(Collection<JobPostCategoryDto> dtoList, String cateName) {
        for (JobPostCategoryDto dto : dtoList) {
            if (cateName.equals(dto.getName())) {
                return true;
            }
        }

        return false;
    }
}
