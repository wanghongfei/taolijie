package com.fh.taolijie.service.quest;

import com.fh.taolijie.domain.quest.QuestModel;

/**
 * 任务对象业务接口
 * Created by whf on 10/28/15.
 */
public interface QuestTargetService {
    void fillTarget(QuestModel quest);

    /**
     * 检查用户的身份与任务对象是否匹配
     * @param memId
     * @param questId
     * @return
     */
    boolean checkTarget(Integer memId, QuestModel quest);
}
