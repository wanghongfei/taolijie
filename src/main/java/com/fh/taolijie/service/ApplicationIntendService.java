package com.fh.taolijie.service;

import com.fh.taolijie.domain.ApplicationIntendModel;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-19.
 */
public interface ApplicationIntendService {
    List<ApplicationIntendModel> getByIntend(Integer categoryId, int pageNumber, int pageSize);
    List<ApplicationIntendModel> getByResume(Integer resumeId);

    List<ApplicationIntendModel> getByResumeInBatch(List<Integer> resumeIdList);

    void addIntend(ApplicationIntendModel model);
    void deleteIntend(ApplicationIntendModel model);

    /**
     * 删除指定简历下的所有意向
     * @param resumeId
     */
    void deleteByResume(Integer resumeId);
}
