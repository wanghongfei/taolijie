package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.FeedbackModel;

import java.util.List;

/**
 * Created by whf on 8/2/15.
 */
public interface FeedbackService {
    /**
     * 查询所有反馈
     * @return
     */
    ListResult<FeedbackModel> getAll(int pageNumber, int pageSize);

    /**
     * 根据id查询反馈
     * @param fbId
     * @return
     */
    FeedbackModel findById(Integer fbId);

    /**
     * 创建一条反馈
     * @param model
     * @return 返回新插入记录的id
     */
    Integer addFeedback(FeedbackModel model);

    /**
     * 根据id更新反馈
     * @param model
     */
    void updateById(FeedbackModel model);

    /**
     * 根据id删除反馈
     * @param fbId
     */
    void deleteById(Integer fbId);

    /**
     * 批量删除.
     * @param idList idList不能为空
     */
    void deleteInBatch(List<Integer> idList);
}
