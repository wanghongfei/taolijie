package com.fh.taolijie.service.acc;

import com.fh.taolijie.domain.SeQuestionModel;

/**
 * Created by whf on 10/25/15.
 */
public interface SeQuestionService {
    /**
     * 根据用户id查询问题
     * @param memId
     * @return
     */
    SeQuestionModel findByMember(Integer memId);

    /**
     * 创建密保问题
     * @param model
     * @return
     */
    int add(SeQuestionModel model);

    /**
     * 删除指定用户的密保问题
     * @param memId
     * @return
     */
    int deleteByMember(Integer memId);
}
