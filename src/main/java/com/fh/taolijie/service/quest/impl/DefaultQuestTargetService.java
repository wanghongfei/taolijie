package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.dao.mapper.*;
import com.fh.taolijie.domain.certi.StuCertiModel;
import com.fh.taolijie.domain.dict.DictCollegeModel;
import com.fh.taolijie.domain.quest.*;
import com.fh.taolijie.service.certi.StuCertiService;
import com.fh.taolijie.service.quest.QuestTargetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by whf on 10/28/15.
 */
@Service
public class DefaultQuestTargetService implements QuestTargetService {
    public static final Logger log = LoggerFactory.getLogger(DefaultQuestTargetService.class);

    @Autowired
    private QuestSchRelModelMapper schMapper;
    @Autowired
    private QuestCollRelModelMapper collMapper;
    @Autowired
    private QuestCiRelMapper cityMapper;
    @Autowired
    private QuestProRelMapper proMapper;

    @Autowired
    private StuCertiService stuService;

    @Autowired
    private DictCollegeModelMapper dictCollMapper;


    @Override
    @Transactional(readOnly = true)
    public void fillTarget(QuestModel quest) {
        Integer questId = quest.getId();

        quest.setSchools(schMapper.selectByQuestId(questId));
        quest.setColleges(collMapper.selectByQuestId(questId));
        quest.setCities(cityMapper.selectByQuestId(questId));
        quest.setPros(proMapper.selectByQuestId(questId));

    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkTarget(Integer memId, Integer questId) {
        // 查询学生的认证信息
        StuCertiModel certi = stuService.findDoneByMember(memId);

        // 查出任务对象

        // 验证学校学校
        List<QuestSchRelModel> schRel = schMapper.selectByQuestId(questId);
        // 非空表示该任务设置了学校任务对象
        if (false == schRel.isEmpty()) {
            // 检查学生所在学校是否在任务对象中
            Integer schId = certi.getSchoolId();
            return schRel.stream().anyMatch( sch -> sch.getId().equals(schId) );
        }

        // 验证城市城市
        List<QuestCiRel> ciRel = cityMapper.selectByQuestId(questId);
        // 查出学生认证所在学校的字典数据
        DictCollegeModel college = dictCollMapper.selectByPrimaryKey(certi.getSchoolId());
        if (false == ciRel.isEmpty()) {
            Integer cityId = college.getCityId();
            return ciRel.stream().anyMatch( ci -> ci.getId().equals(cityId) );
        }

        // 验证省
        List<QuestProRel> proRel = proMapper.selectByQuestId(questId);
        if (false == ciRel.isEmpty()) {
            Integer proId = college.getProvinceId();
            return proRel.stream().anyMatch( pro -> pro.getId().equals(proId) );
        }


        log.error("Matching quest target failed");

        return false;
    }
}
