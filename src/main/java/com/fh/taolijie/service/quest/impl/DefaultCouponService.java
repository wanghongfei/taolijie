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
import com.fh.taolijie.dto.CouponInfoDto;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.quest.*;
import com.fh.taolijie.service.quest.CouponService;
import com.fh.taolijie.utils.StringUtils;
import com.fh.taolijie.utils.TimeUtil;
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

    @Autowired
    private CouponModelMapper couponMapper;

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
        String nowDate = TimeUtil.today();
        for (int ix = 0 ; ix < amt ; ++ix) {
            //String code = RandomStringUtils.randomAlphabetic(30);
            String code = RandomStringUtils.randomNumeric(20);
            String finalCode = StringUtils.concat(30, nowDate, code);
            codeList.add(finalCode);
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

    @Override
    @Transactional(readOnly = true)
    public ListResult<CouponInfoDto> findByEmp(Integer empId, int pn, int ps) {
        // 查出employee发的带卡券的任务
        QuestModel questCmd = new QuestModel(pn, ps);
        questCmd.setMemberId(empId);
        questCmd.setCoupon(true);
        List<QuestModel> questList = questMapper.findBy(questCmd);
        // 如果商家没有发布任务任务
        // 则终止后续查询
        if (questList.isEmpty()) {
            return new ListResult<>();
        }

        long tot = questMapper.countFindBy(questCmd);

        // 将任务id转换成List
        List<Integer> idList = StringUtils.model2IdList(questList);

        // 查出任务对应的coupon
        List<CouponModel> couponList = couponMapper.selectOneGroupByQuestId(idList);

        // 生成DTO对象
        int LEN = idList.size();
        List<CouponInfoDto> dtoList = new ArrayList<>(LEN);
        for (int ix = 0 ; ix < LEN ; ++ix) {
            QuestModel quest = questList.get(ix);
            dtoList.add(new CouponInfoDto(couponList.get(ix), quest.getTotalAmt(), quest.getLeftAmt()));
        }


        return new ListResult<>(dtoList, tot);
    }
}
