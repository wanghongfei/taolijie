package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.FeedbackModelMapper;
import com.fh.taolijie.domain.FeedbackModel;
import com.fh.taolijie.domain.FeedbackModelExample;
import com.fh.taolijie.service.FeedbackService;
import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by whf on 8/2/15.
 */
@Service
@Transactional(readOnly = true)
public class DefaultFeedbackService implements FeedbackService {
    @Autowired
    private FeedbackModelMapper fdMapper;

    @Override
    public ListResult<FeedbackModel> getAll(int pageNumber, int pageSize) {
        FeedbackModelExample example = new FeedbackModelExample(pageNumber, pageSize);
        example.setOrderByClause("created_time DESC");


        List<FeedbackModel> list = fdMapper.selectByExample(example);
        long tot = fdMapper.countByExample(example);

        return new ListResult<>(list, tot);
    }

    @Override
    public FeedbackModel findById(Integer fbId) {
        return fdMapper.selectByPrimaryKey(fbId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer addFeedback(FeedbackModel model) {
        fdMapper.insertSelective(model);

        return model.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateById(FeedbackModel model) {
        fdMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(Integer fbId) {
        fdMapper.deleteByPrimaryKey(fbId);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteInBatch(List<Integer> idList) {
        fdMapper.deleteByPkInBatch(idList);
    }
}
