package com.fh.taolijie.service.certi.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.dao.mapper.EmpCertiModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.certi.EmpCertiModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.service.certi.EmpCertiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 商家认证业务实现
 * Created by whf on 9/22/15.
 */
@Service
public class DefaultEmpCertiService implements EmpCertiService {
    @Autowired
    private EmpCertiModelMapper certiMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addApplication(EmpCertiModel model) {

/*        // 检查是否存在该用户申请的且状态为通过的记录
        if (certiMapper.checkApplyExists(model.getMemberId())) {
            // 如果有，则不允许再申请
            throw new ApplicationDuplicatedException("");
        }*/

        Date now = new Date();

        model.setStatus(CertiStatus.WAIT_AUDIT.code());
        model.setCreatedTime(now);
        model.setUpdateTime(now);

        // 填写username冗余字段
        MemberModel m = memMapper.selectByPrimaryKey(model.getMemberId());
        model.setUsername(m.getUsername());

        certiMapper.insertSelective(model);

        // 更新用户表认证状态
        MemberModel example = new MemberModel();
        example.setId(m.getId());
        example.setEmpCerti(CertiStatus.WAIT_AUDIT.code());
        memMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateStatus(Integer certiId, Integer memId, CertiStatus status, String memo) {

        EmpCertiModel example = new EmpCertiModel();
        example.setId(certiId);
        example.setMemo(memo);
        example.setStatus(status.code());
        certiMapper.updateByPrimaryKeySelective(example);

        // 更新用户表认证状态
        MemberModel memExample = new MemberModel();
        memExample.setId(memId);
        memExample.setEmpCerti(CertiStatus.DONE.code());
        memMapper.updateByPrimaryKeySelective(memExample);

    }

    @Override
    @Transactional(readOnly = true)
    public EmpCertiModel findById(Integer certiId) {
        return certiMapper.selectByPrimaryKey(certiId);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<EmpCertiModel> findByMember(Integer memId) {
        EmpCertiModel example = new EmpCertiModel();
        example.setMemberId(memId);

        List<EmpCertiModel> list = certiMapper.findBy(example);
        long tot = certiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }
}
