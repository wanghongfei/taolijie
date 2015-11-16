package com.fh.taolijie.service.certi.impl;

import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.dao.mapper.IdCertiModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.IdCertiModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.exception.checked.certi.ApplicationDuplicatedException;
import com.fh.taolijie.service.certi.IdCertiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by whf on 11/16/15.
 */
@Service
public class DefaultIdCertiService implements IdCertiService {
    @Autowired
    private IdCertiModelMapper idMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addApplication(IdCertiModel model) throws ApplicationDuplicatedException {
        // 先检查是否已经认证过了
        MemberModel mem = memMapper.selectByPrimaryKey(model.getMemId());
        String status = mem.getIdCerti();
        if (null != status && status.equals(CertiStatus.DONE.code())) {
            throw new ApplicationDuplicatedException("");
        }


        Date now = new Date();
        model.setCreatedTime(now);

        // 填写username冗余字段
        model.setUsername(mem.getUsername());
        // 设置认证状态
        model.setStatus(CertiStatus.WAIT_AUDIT.code());
        idMapper.insertSelective(model);

        // 更新member表认证状态
        MemberModel example = new MemberModel();
        example.setId(mem.getId());
        example.setIdCerti(CertiStatus.WAIT_AUDIT.code());
        memMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateStatus(Integer certiId, Integer memId, CertiStatus status, String memo) {
        // 更新认证表状态
        Date now = new Date();
        IdCertiModel idExample = new IdCertiModel();
        idExample.setId(certiId);
        idExample.setUpdateTime(now);
        idExample.setStatus(status.code());
        idExample.setMemo(memo);
        idMapper.updateByPrimaryKeySelective(idExample);

        // 更新用户表状态
        MemberModel mem = new MemberModel();
        mem.setId(memId);
        mem.setIdCerti(status.code());
        memMapper.updateByPrimaryKeySelective(mem);
    }

    @Override
    @Transactional(readOnly = true)
    public IdCertiModel findById(Integer certiId) {
        return idMapper.selectByPrimaryKey(certiId);
    }
}
