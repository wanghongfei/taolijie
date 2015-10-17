package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.RecoType;
import com.fh.taolijie.dao.mapper.RecoPostModelMapper;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.exception.checked.PostNotFoundException;
import com.fh.taolijie.service.RecoService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.sh.ShPostService;
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

    @Autowired
    private JobPostService jobService;

    @Autowired
    private ShPostService shService;

    @Autowired
    private QuestService questService;

    @Override
    @Transactional(readOnly = true)
    public ListResult<RecoPostModel> findBy(RecoPostModel example) {
        List<RecoPostModel> list = reMapper.findBy(example);
        long tot = reMapper.countFindBy(example);


        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = false)
    public int add(RecoPostModel model) throws PostNotFoundException {
        model.setCreatedTime(new Date());

        // 填写冗余字段title
        RecoType type = RecoType.fromCode(model.getType());
        String title = null;
        Integer postId = model.getPostId();
        switch (type) {
            case JOB:
                JobPostModel job = jobService.findJobPost(postId);
                if (null == job) {
                    throw new PostNotFoundException();
                }
                title = job.getTitle();

                break;

            case SH:
                SHPostModel sh = shService.findPost(postId);
                if (null == sh) {
                    throw new PostNotFoundException();
                }
                title = sh.getTitle();

                break;

            case QUEST:
                QuestModel quest = questService.findById(postId);
                if (null == quest) {
                    throw new PostNotFoundException();
                }
                title = quest.getTitle();

                break;
        }
        model.setTitle(title);

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
