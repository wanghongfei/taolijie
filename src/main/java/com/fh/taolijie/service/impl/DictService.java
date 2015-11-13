package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.DictIndustryModelMapper;
import com.fh.taolijie.domain.DictIndustryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whf on 11/13/15.
 */
@Service
public class DictService {
    @Autowired
    private DictIndustryModelMapper indMapper;

    /**
     * 查询全部行业分类信息
     * @return
     */
    public Map<DictIndustryModel, List<DictIndustryModel>> findIndustryList() {
        List<DictIndustryModel> list = indMapper.selectAll();

        return setChildClass(list);
    }

    /**
     * 设置子分类
     * @param list
     */
    private Map<DictIndustryModel, List<DictIndustryModel>> setChildClass(List<DictIndustryModel> list) {
        Map<DictIndustryModel, List<DictIndustryModel>> map = new HashMap<>(8);

        // 找到父分类
        list.stream()
                .filter(dict -> null == dict.getParentId())
                .forEach(dict -> map.put(dict, new ArrayList<>(15)));

        // 设置子分类
        list.stream()
                .filter( dict -> null != dict.getParentId() )
                .forEach( dict -> map.get(new DictIndustryModel(dict.getParentId())).add(dict) );

        return map;
    }
}
