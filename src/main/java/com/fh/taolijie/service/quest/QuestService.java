package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.QuestModel;

import java.math.BigDecimal;

/**
 * 轻兼职业务接口
 * Created by whf on 9/21/15.
 */
public interface QuestService {
    /**
     * 商家发布任务
     * @param model
     */
    void publishQuest(QuestModel model);

    /**
     * 根据分类查找任务
     * @param cateId 分类id
     * @return
     */
    ListResult<QuestModel> findByCate(Integer cateId, int pn, int ps);

    /**
     * 根据分类和赏金查询任务
     * @param cateId 分类id
     * @param min 最低赏金
     * @param max 最高赏金
     * @return
     */
    ListResult<QuestModel> findByCate(Integer cateId, BigDecimal min, BigDecimal max, int pn, int ps);
}
