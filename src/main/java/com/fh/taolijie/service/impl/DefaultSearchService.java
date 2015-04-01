package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.SearchService;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wanghongfei on 15-4-1.
 */
@Service
public class DefaultSearchService implements SearchService {
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <T> List<T> runLikeQuery(Class<T> type, Map<String, Object> likeParam, Map.Entry<String, String> orderBy, EntityManager em) {
        String query = StringUtils.buildLikeQuery(type.getSimpleName(), likeParam, orderBy);

        TypedQuery<T> queryObj = em.createQuery(query, type);

        // 给参数赋值
        Set<Parameter<?>> parmSet = queryObj.getParameters();
        for ( Parameter parm : parmSet) {
            String parmName = parm.getName();
            Object parmValue = likeParam.get(parmName);

            queryObj.setParameter(parmName, parmValue);
        }

        // 执行查询
        List<T> entityList = queryObj
                //.setFirstResult(firstResult)
                //.setMaxResults(CollectionUtils.determineCapacity(capacity))
                .getResultList();

        return entityList;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <T> List<T> runAccurateQuery(Class<T> type, Map<String, Object> likeParam, Map.Entry<String, String> orderBy, EntityManager em) {
        String query = StringUtils.buildQuery("obj", type.getSimpleName(), likeParam, orderBy);

        TypedQuery<T> queryObj = em.createQuery(query, type);

        // 给参数赋值
        Set<Parameter<?>> parmSet = queryObj.getParameters();
        for ( Parameter parm : parmSet) {
            String parmName = parm.getName();
            Object parmValue = likeParam.get(parmName);

            queryObj.setParameter(parmName, parmValue);
        }

        // 执行查询
        List<T> entityList = queryObj
                //.setFirstResult(firstResult)
                //.setMaxResults(CollectionUtils.determineCapacity(capacity))
                .getResultList();

        return entityList;
    }
}
