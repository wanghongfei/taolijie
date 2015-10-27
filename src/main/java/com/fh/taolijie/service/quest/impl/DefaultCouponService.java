package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.constant.quest.CouponStatus;
import com.fh.taolijie.dao.mapper.CouponModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.QuestModelMapper;
import com.fh.taolijie.domain.CouponModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;
import com.fh.taolijie.service.quest.CouponService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by whf on 10/27/15.
 */
@Service
public class DefaultCouponService implements CouponService {
    @Autowired
    private CouponModelMapper couMapper;

    @Autowired
    private QuestModelMapper questMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int add(CouponModel model, QuestModel quest) {
        // 填写冗余字段
        // quest_title
        model.setQuestTitle(quest.getTitle());
        model.setQuestId(quest.getId());

        // comp_name
        MemberModel mem = memMapper.selectByPrimaryKey(model.getEmpId());
        model.setCompName(mem.getCompanyName());

        model.setCreatedTime(new Date());
        model.setStatus(CouponStatus.NOT_ASSIGNED.code());

        // 生成code值
        int amt = model.getAmt();
        List<String> codeList = new ArrayList<>(amt);
        for (int ix = 0 ; ix < amt ; ++ix) {
            codeList.add(RandomStringUtils.randomAlphabetic(30));
        }

        // 批量插入
        return couMapper.insertInBatch(codeList, model);
    }
}
