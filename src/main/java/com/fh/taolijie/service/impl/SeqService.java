package com.fh.taolijie.service.impl;

import com.fh.taolijie.constant.PicType;
import com.fh.taolijie.dao.mapper.SeqAvatarModelMapper;
import com.fh.taolijie.dao.mapper.SeqJobModelMapper;
import com.fh.taolijie.dao.mapper.SeqShModelMapper;
import com.fh.taolijie.domain.SeqAvatarModel;
import com.fh.taolijie.domain.SeqJobModel;
import com.fh.taolijie.domain.SeqShModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by whf on 9/25/15.
 */
@Service
public class SeqService {
    @Autowired
    private SeqAvatarModelMapper avaMapper;

    @Autowired
    private SeqShModelMapper shMapper;

    @Autowired
    private SeqJobModelMapper jobMapper;


    public String genKey(PicType type) {
        switch (type) {
            case JOB:
                return genJobKey();

            case SH:
                return genShKey();

            case AVATAR:
                return genAvatarKey();
        }

        return null;
    }

    public String genAvatarKey() {
        SeqAvatarModel model = new SeqAvatarModel();
        avaMapper.insert(model);

        return "avatar-" + model.getId();
    }

    public String genShKey() {
        SeqShModel model = new SeqShModel();
        shMapper.insert(model);

        return "sh-" + model.getId();
    }

    public String genJobKey() {
        SeqJobModel model = new SeqJobModel();
        jobMapper.insert(model);

        return "job-" + model.getId();
    }
}
