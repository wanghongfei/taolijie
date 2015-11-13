package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.dao.mapper.DictCityModelMapper;
import com.fh.taolijie.dao.mapper.DictCollegeModelMapper;
import com.fh.taolijie.dao.mapper.DictProvinceModelMapper;
import com.fh.taolijie.domain.DictIndustryModel;
import com.fh.taolijie.domain.dict.DictCityModel;
import com.fh.taolijie.domain.dict.DictCollegeModel;
import com.fh.taolijie.domain.dict.DictProvinceModel;
import com.fh.taolijie.domain.dict.DictSchoolModel;
import com.fh.taolijie.service.impl.DictService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by whf on 11/10/15.
 */
@RestController
@RequestMapping("/api/dict")
public class RestDictCtr {
    @Autowired
    private DictProvinceModelMapper proMapper;

    @Autowired
    private DictCityModelMapper cityMapper;

    @Autowired
    private DictCollegeModelMapper collegeMapper;

    @Autowired
    private DictService dictService;

    /**
     * 查询省信息
     * @return
     */
    @RequestMapping(value = "/province", produces = Constants.Produce.JSON)
    public ResponseText province() {
        List<DictProvinceModel> proList = proMapper.selectAll();
        ListResult<DictProvinceModel> lr = new ListResult<>(proList, proList.size());

        return new ResponseText(lr);
    }

    /**
     * 查询城市信息
     * @return
     */
    @RequestMapping(value = "/city", produces = Constants.Produce.JSON)
    public ResponseText city() {
        List<DictCityModel> cityList = cityMapper.selectAll();
        ListResult<DictCityModel> lr = new ListResult<>(cityList, cityList.size());

        return new ResponseText(lr);
    }

    /**
     * 查询学校信息
     * @return
     */
    @RequestMapping(value = "/college", produces = Constants.Produce.JSON)
    public ResponseText school() {
        List<DictCollegeModel> collList = collegeMapper.selectAll();
        ListResult<DictCollegeModel> lr = new ListResult<>(collList, collList.size());

        return new ResponseText(lr);
    }

    /**
     * 行业分类
     * @return
     */
    @RequestMapping(value = "/industry", produces = Constants.Produce.JSON)
    public ResponseText industry() {
        Map<DictIndustryModel, List<DictIndustryModel>> map = dictService.findIndustryList();

        return new ResponseText(map);
    }
}
