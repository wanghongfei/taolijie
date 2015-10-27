package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.quest.CouponStatus;
import com.fh.taolijie.dao.mapper.CouponModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.QuestAssignModelMapper;
import com.fh.taolijie.dao.mapper.QuestModelMapper;
import com.fh.taolijie.domain.CouponModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.quest.*;
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
    private QuestAssignModelMapper assignMapper;

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

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public CouponModel acquireCoupon(Integer questId, Integer memId) throws NotEnoughCouponException, HackException {
        // 检查用户是否已经领取了该任务
        Boolean assigned = assignMapper.checkMemberIdAndQuestIdExists(memId, questId);
        // 任务没有领取
        if (false == assigned) {
            throw new HackException();
        }


        // 领取coupon
        CouponModel coupon = couMapper.selectOneByQuestIdWithLock(questId);
        // 返回null表示领取失败
        if (null == coupon) {
            throw new NotEnoughCouponException();
        }

        // 将取到的coupon状态设为已领取
        CouponModel example = new CouponModel();
        example.setId(coupon.getId());
        example.setStatus(CouponStatus.ASSIGNED.code());
        example.setAcquireTime(new Date());
        example.setMemId(memId);
        couMapper.updateByPrimaryKeySelective(example);

        // 将对应任务的coupon数量-1
        questMapper.decreaseCouponAmt(questId);

        return coupon;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean validateCoupon(String code) throws CouponNotFoundException, InvalidCouponException, CouponUsedException, CouponExpiredException {
        CouponModel coupon = couMapper.selectByCode(code);
        if (null == coupon) {
            throw new CouponNotFoundException();
        }

        // 检查coupon状态
        int status = coupon.getStatus().intValue();
        if (CouponStatus.NOT_ASSIGNED.code() == status) {
            // coupon还没有创建
            throw new InvalidCouponException();
        }
        if (CouponStatus.USED.code() == status) {
            // 已经使用过了
            throw new CouponUsedException();
        }

        // 检查是否过期
        if (coupon.getExpiredTime().compareTo(new Date()) < 0) {
            throw new CouponExpiredException();
        }

        // 标记coupon为已经使用
        CouponModel example = new CouponModel();
        example.setId(coupon.getId());
        example.setStatus(CouponStatus.USED.code());
        couMapper.updateByPrimaryKeySelective(example);


        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<CouponModel> findBy(CouponModel model) {
        List<CouponModel> list = couMapper.findBy(model);
        long tot = couMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }
}
