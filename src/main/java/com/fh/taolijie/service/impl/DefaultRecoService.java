package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.PostType;
import com.fh.taolijie.constant.RecoType;
import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.dao.mapper.RecoPostModelMapper;
import com.fh.taolijie.dao.mapper.SysConfigModelMapper;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.domain.SysConfigModel;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.exception.checked.PostNotFoundException;
import com.fh.taolijie.exception.checked.RecoRepeatedException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.RecoService;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by whf on 10/15/15.
 */
@Service
public class DefaultRecoService implements RecoService {
    @Autowired
    private RecoPostModelMapper reMapper;

    @Autowired
    private JobPostService jobService;

    @Autowired
    private JobPostModelMapper jobMapper;

    @Autowired
    private ShPostService shService;

    @Autowired
    private QuestService questService;

    @Autowired
    private SysConfigModelMapper sysMapper;

    @Autowired
    private CashAccService accService;

    @Override
    @Transactional(readOnly = true)
    public ListResult<RecoPostModel> findBy(RecoPostModel example) {
        List<RecoPostModel> list = reMapper.findBy(example);
        long tot = reMapper.countFindBy(example);


        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int add(RecoPostModel model)
            throws PostNotFoundException, RecoRepeatedException, CashAccNotExistsException, BalanceNotEnoughException {
        Integer postId = model.getPostId();


        // 重复性检查
        // 重复的条件为:
        // 1. postId相同
        // 2. type相同
        // 3. 没有过期
        boolean repeat = reMapper.checkExist(postId, model.getType());
        if (repeat) {
            throw new RecoRepeatedException();
        }



        model.setCreatedTime(new Date());

        // 填写冗余字段title
        String title = null;
        RecoType type = RecoType.fromCode(model.getType());
        switch (type) {
            case JOB:
                JobPostModel job = jobService.findJobPost(postId);
                if (null == job) {
                    throw new PostNotFoundException();
                }
                title = job.getTitle();

                break;

            case SH:
                SHPostModel sh = shService.findPost(postId);
                if (null == sh) {
                    throw new PostNotFoundException();
                }
                title = sh.getTitle();

                break;

            case QUEST:
                QuestModel quest = questService.findById(postId);
                if (null == quest) {
                    throw new PostNotFoundException();
                }
                title = quest.getTitle();

                break;
        }
        model.setTitle(title);
        // 插入到推荐表中
        reMapper.insertSelective(model);

        // 扣钱
        // 查询费用设置
        SysConfigModel sys = sysMapper.selectByPrimaryKey(1);
        BigDecimal singleFee = sys.getTopFee();
        BigDecimal hours = new BigDecimal(model.getHours());
        BigDecimal finalFee = singleFee.multiply(hours);

        // 扣除余额
        Integer accId = accService.findIdByMember(model.getMemberId());
        accService.reduceAvailableMoney(accId, finalFee, AccFlow.CONSUME);

        return model.getId();
    }

    /**
     * 标签推荐
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int addTag(Integer postId, RecoType postType, int hours)
            throws PostNotFoundException, BalanceNotEnoughException, CashAccNotExistsException {

        Date newDate = null;
        Integer memId = null;

        if (postType == RecoType.QUEST) {
            // 检查帖子存在性
            QuestModel quest = questService.findById(postId);
            if (null == quest) {
                throw new PostNotFoundException();
            }
            memId = quest.getMemberId();

            // 计算新过期时间
            newDate = TimeUtil.calculateDate(quest.getTagExpireTime(), Calendar.HOUR_OF_DAY, hours);
        } else {
            throw new IllegalStateException("invalid postType!");
        }


        // 扣钱
        // 计算钱数
        SysConfigModel sys = sysMapper.selectByPrimaryKey(1);
        BigDecimal singleFee = sys.getTagFee();
        BigDecimal finalFee = singleFee.multiply(new BigDecimal(hours));

        // 从现金账户中扣除
        Integer accId = accService.findIdByMember(memId);
        accService.reduceAvailableMoney(accId, finalFee, AccFlow.CONSUME);


        // 更新tag过期时间
        questService.updateTagExpireTime(postId, newDate);

        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int update(RecoPostModel model) {
        return reMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int delete(Integer id) {
        return reMapper.deleteByPrimaryKey(id);
    }
}
