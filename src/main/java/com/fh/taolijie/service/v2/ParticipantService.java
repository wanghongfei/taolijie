package com.fh.taolijie.service.v2;

import com.fh.taolijie.domain.ParticipantModel;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-10.
 */
public interface ParticipantService {
    /**
     * 报名参加一个activity或ivy
     * @param memberId
     */
    void participate(ParticipantModel model);

    /**
     * 得到ivy或校园活动的报名信息.
     * dto中的memberId为必填值. ivyId和activityId必须至少有一个有值
     * @param page
     * @param capacity
     * @param wrapper
     * @return
     */
    List<ParticipantModel> getParticipantList(ParticipantModel model, int page, int capacity, ObjWrapper wrapper);
}
