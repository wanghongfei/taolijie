package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.OffQuestModelMapper;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.service.quest.OffQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.beans.Transient;

/**
 * Created by whf on 11/30/15.
 */
@ServiceMode
public class DefaultOffQuestService implements OffQuestService {
    @Autowired
    private OffQuestModelMapper questMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void publish(OffQuestModel model) {

    }

    @Override
    public ListResult<OffQuestModel> findBy(OffQuestModel cmd) {
        return null;
    }

    @Override
    public int distance(double start, double end) {
        return 0;
    }
}
