package com.fh.taolijie.service.acc;

import com.fh.taolijie.domain.SeQuestionModel;
import com.fh.taolijie.exception.checked.acc.SecretQuestionExistException;

/**
 * Created by whf on 10/25/15.
 */
public interface SeQuestionService {
    /**
     * 根据用户id查询问题
     * @param memId
     * @param answer 是否包括答案
     * @return
     */
    SeQuestionModel findByMember(Integer memId, boolean answer);

    /**
     * 创建密保问题
     * @param model
     * @return
     */
    int add(SeQuestionModel model) throws SecretQuestionExistException;


    /**
     * 删除指定用户的密保问题
     * @param memId
     * @return
     */
    int deleteByMember(Integer memId);


    /**
     * 检查问题答案是否正确
     * @param memId
     * @param answer
     * @return
     */
    boolean checkAnswer(Integer memId, String answer);
}
