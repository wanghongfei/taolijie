package com.fh.taolijie.service.impl.v2;

import com.fh.taolijie.dao.mapper.v2.ParticipantModelMapper;
import com.fh.taolijie.domain.ParticipantModel;
import com.fh.taolijie.service.v2.ParticipantService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-11.
 */
@Service
@Transactional(readOnly = true)
public class DefaultParticipantService implements ParticipantService {
    @Autowired
    ParticipantModelMapper parMapper;

    @Override
    @Transactional(readOnly = false)
    public void participate(ParticipantModel model) {
        parMapper.insert(model);
    }

    @Override
    public List<ParticipantModel> getParticipantList(ParticipantModel model, int page, int capacity, ObjWrapper wrapper) {
        model.setPageNumber(page);
        model.setPageSize(CollectionUtils.determineCapacity(capacity));

        return parMapper.findBy(model);
    }
}
