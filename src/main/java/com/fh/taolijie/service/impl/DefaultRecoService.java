package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.RecoPostModelMapper;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.service.RecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by whf on 10/15/15.
 */
@Service
public class DefaultRecoService implements RecoService {
    @Autowired
    private RecoPostModelMapper reMapper;

    @Override
    @Transactional(readOnly = true)
    public ListResult<RecoPostModel> findBy(RecoPostModel example) {
        List<RecoPostModel> list = reMapper.findBy(example);
        long tot = reMapper.countFindBy(example);


        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = false)
    public int add(RecoPostModel model) {
        model.setCreatedTime(new Date());
        reMapper.insertSelective(model);

        return model.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public int update(RecoPostModel model) {
        return reMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(Integer id) {
        return reMapper.deleteByPrimaryKey(id);
    }
}
