package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.quest.OffQuestStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.OffCateModelMapper;
import com.fh.taolijie.dao.mapper.OffQuestModelMapper;
import com.fh.taolijie.domain.OffCateModel;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.PostIntervalException;
import com.fh.taolijie.exception.checked.certi.IdUnverifiedException;
import com.fh.taolijie.service.certi.IdCertiService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.service.quest.OffQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * Created by whf on 11/30/15.
 * @author wanghongfei
 */
@Service
public class DefaultOffQuestService implements OffQuestService {
    @Autowired
    private OffQuestModelMapper questMapper;

    @Autowired
    private IdCertiService idService;

    @Autowired
    private IntervalCheckService intervalService;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private OffCateModelMapper cateMapper;


    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void publish(OffQuestModel model) throws GeneralCheckedException {
        Integer memId = model.getMemId();

        // 发布时间间隔检查
        if (false == intervalService.checkOffQuestPostInterval(memId)) {
            throw new PostIntervalException();
        }

        // 检查用户是否完成了id认证
        boolean verified = idService.checkVerified(memId);
        if (false == verified) {
            throw new IdUnverifiedException();
        }


        // 执行发布
        model.setCreatedTime(new Date());
        // 设置username冗余字段
        String username = memMapper.selectUsername(memId);
        model.setUsername(username);
        // 设置cate_name冗余字段
        OffCateModel cate = cateMapper.selectByPrimaryKey(model.getCateId());
        model.setCateName(cate.getName());
        // 设置状态为已发布
        model.setStatus(OffQuestStatus.PUBLISHED.code());

        questMapper.insertSelective(model);
    }

    @Override
    public ListResult<OffQuestModel> findBy(OffQuestModel cmd) {
        return null;
    }

    @Override
    public int distance(double start, double end) {
        return 0;
    }
}
