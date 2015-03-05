package com.fh.taolijie.utils;

import com.fh.taolijie.domain.RoleEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by wanghongfei on 15-3-5.
 */
@Component
@Repository
public class RoleImport {
    @PersistenceContext
    private EntityManager em;

    /**
     * 如果role表中没有记录，则向表中插入预定好的role记录
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void importRoles() {
        Long rows = em.createQuery("SELECT COUNT(r) FROM Role r", Long.class)
                .getSingleResult();

        // role表中没有记录,
        // 则添加记录
        if (0 == rows.intValue()) {
            Constants.RoleType[] types = Constants.RoleType.values();

            for (Constants.RoleType role : types) {
                em.persist(new RoleEntity(role.toString(), ""));
            }
        }
    }
}
