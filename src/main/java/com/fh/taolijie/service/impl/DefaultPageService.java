package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.PageService;
import com.fh.taolijie.service.PageViewAware;
import com.fh.taolijie.utils.CheckUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by wanghongfei on 15-3-30.
 */
@Service
public class DefaultPageService implements PageService {
    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public <T extends PageViewAware> void increasePageView(Integer entityId, Class<T> type) {
        T entity = em.find(type, entityId);
        CheckUtils.nullCheck(entity);

        Integer original = entity.getPageView();
        Integer newValue = original == null ? 1 : original.intValue() + 1;

        entity.setPageView(newValue);
    }
}
