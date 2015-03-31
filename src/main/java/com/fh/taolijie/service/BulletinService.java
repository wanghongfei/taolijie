package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.BulletinDto;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-31.
 */
public interface BulletinService {
    void addBulletin(BulletinDto dto);

    void deleteBulletin(Integer id);

    /**
     * 查询所有公告
     */
    List<BulletinDto> getAll(int firstResult, int capacity, ObjWrapper wrap);

    BulletinDto findOne(Integer id);
}
