package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.SecondHandPostCategoryDto;
import com.fh.taolijie.domain.SecondHandPostCategoryEntity;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.SHPostCategoryService;
import com.fh.taolijie.service.repository.SHPostCategoryRepo;
import com.fh.taolijie.utils.CheckUtils;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-8.
 */
@Service
public class DefaultSHPostCategoryService implements SHPostCategoryService {
    @Autowired
    SHPostCategoryRepo cateRepo;

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostCategoryDto> getCategoryList(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0 ) {
            cap = Constants.PAGE_CAPACITY;
        }

        Page<SecondHandPostCategoryEntity> catePages = cateRepo.findAll(new PageRequest(firstResult, cap));
        wrapper.setObj(catePages.getTotalPages());

        List<SecondHandPostCategoryDto> dtoList = new ArrayList<>();
        for (SecondHandPostCategoryEntity cate : catePages) {
            //dtoList.add(makeCateDto(cate));
            dtoList.add(CollectionUtils.entity2Dto(cate, SecondHandPostCategoryDto.class, null));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public SecondHandPostCategoryDto findCategory(Integer cateId) {
        SecondHandPostCategoryEntity entity = cateRepo.findOne(cateId);
        CheckUtils.nullCheck(entity);

        return CollectionUtils.entity2Dto(entity, SecondHandPostCategoryDto.class, null);
        //return makeCateDto(cateRepo.findOne(cateId));
    }

    @Override
    @Transactional(readOnly = false)
    public void addCategory(SecondHandPostCategoryDto dto) {
        SecondHandPostCategoryEntity entity = CollectionUtils.dto2Entity(dto, SecondHandPostCategoryEntity.class, null);
        cateRepo.save(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateCategory(Integer cateId, SecondHandPostCategoryDto cateDto) {
        SecondHandPostCategoryEntity cate = cateRepo.findOne(cateId);
        CheckUtils.nullCheck(cate);

        CollectionUtils.updateEntity(cate, cateDto, null);

        //updateCategory(cate, cateDto);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteCategory(Integer cateId) throws CascadeDeleteException {
        SecondHandPostCategoryEntity cate = cateRepo.findOne(cateId);
        CheckUtils.nullCheck(cate);

        // 检查分类是否为空
        if (cate.getPostCollection() != null && false == cate.getPostCollection().isEmpty()) {
            throw new CascadeDeleteException("分类" + cate.getName() + "不为空");
        }

        // 删除实体
        cateRepo.delete(cate);

        return true;
    }
}
