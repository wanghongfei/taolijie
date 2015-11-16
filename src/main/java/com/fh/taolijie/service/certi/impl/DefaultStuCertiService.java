package com.fh.taolijie.service.certi.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.StuCertiModelMapper;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.certi.StuCertiModel;
import com.fh.taolijie.service.certi.StuCertiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 学生认证业务实现
 * Created by whf on 9/22/15.
 */
@Service
public class DefaultStuCertiService implements StuCertiService {
    @Autowired
    private StuCertiModelMapper certiMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addApplication(StuCertiModel model) {

/*        // 检查是否已经有该用户的申请，且申请状态为通过的记录
        if (certiMapper.checkApplyExists(model.getMemberId())) {
            // 如果有，则不能再提申请
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

        // 更新member表的学生认证状态
        MemberModel example = new MemberModel();
        example.setId(m.getId());
        example.setStuCerti(CertiStatus.WAIT_AUDIT.code());
        memMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateStatus(Integer certiId, Integer memId, CertiStatus status, String memo) {

        // 更新认证表
        StuCertiModel example = new StuCertiModel();
        example.setId(certiId);
        example.setMemo(memo);
        example.setStatus(status.code());
        certiMapper.updateByPrimaryKeySelective(example);

        // 更新用户表认证状态
        MemberModel memExample = new MemberModel();
        memExample.setId(memId);
        memExample.setStuCerti(CertiStatus.DONE.code());
        memMapper.updateByPrimaryKeySelective(memExample);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkVerified(Integer memId) {
        String status = memMapper.selectStuVerified(memId);
        if (null != status && status.equals(CertiStatus.DONE.code())) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public StuCertiModel findById(Integer certiId) {
        return certiMapper.selectByPrimaryKey(certiId);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<StuCertiModel> findByMember(Integer memId) {
        StuCertiModel example = new StuCertiModel();
        example.setMemberId(memId);

        List<StuCertiModel> list = certiMapper.findBy(example);
        long tot = certiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }
}
