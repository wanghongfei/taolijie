package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.quest.OffQuestStatus;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.HTTPConnectionException;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;

import java.math.BigDecimal;

/**
 * 线下任务业务接口
 * Created by whf on 11/30/15.
 */
public interface OffQuestService {
    /**
     * 发布线下任务
     * @param model
     */
    void publish(OffQuestModel model) throws GeneralCheckedException;

    /**
     * 条件查询
     * @param cmd
     * @return
     */
    ListResult<OffQuestModel> findBy(OffQuestModel cmd);

    /**
     * 任务下线
     */
    void updateStatus(Integer questId, Integer memId, OffQuestStatus status) throws GeneralCheckedException;

    /**
     * 刷新发布时间
     * @param questId
     * @param memId
     * @throws GeneralCheckedException
     */
    void flush(Integer questId, Integer memId) throws GeneralCheckedException;

    /**
     * 查询附近的任务
     * @param longitude
     * @param latitude
     * @return
     */
    ListResult<OffQuestModel> nearbyQuest(BigDecimal longitude, BigDecimal latitude, int radis, int pn, int ps) throws GeneralCheckedException;

    /**
     * 根据经纬度计算两点之间的距离
     * @param start 经度
     * @param end 纬度
     * @return 单位为米
     */
    int distance(double start, double end);
}
