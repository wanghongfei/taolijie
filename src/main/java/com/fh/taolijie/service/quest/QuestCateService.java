package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.QuestCategoryModel;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;

/**
 * 轻兼职分类
 * Created by whf on 9/18/15.
 */
public interface QuestCateService {
    QuestCategoryModel find(Integer cateId);

    QuestCategoryModel find(String name);

    ListResult<QuestCategoryModel> findAll(int pn, int ps);

    /**
     * 创建一个分类
     * @param model
     * @return 返回刚创建分类的id
     */
    int add(QuestCategoryModel model);

    /**
     * 返回影响的记录数
     * @param model
     * @return
     */
    int update(QuestCategoryModel model);

    void delete(Integer id)
            throws CategoryNotEmptyException;
}
