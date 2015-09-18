package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.QuestCategoryModel;

/**
 * 轻兼职分类
 * Created by whf on 9/18/15.
 */
public interface QuestCateService {
    QuestCategoryModel find(Integer cateId);

    QuestCategoryModel find(String name);

    ListResult<QuestCategoryModel> findAll(int pn, int ps);

    void add(QuestCategoryModel model);

    void update(QuestCategoryModel model);

    void delete(Integer id);
}
