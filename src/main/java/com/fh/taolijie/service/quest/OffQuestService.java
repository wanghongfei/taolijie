package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

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
     * 根据经纬度计算两点之间的距离
     * @param start 经度
     * @param end 纬度
     * @return 单位为米
     */
    int distance(double start, double end);
}
