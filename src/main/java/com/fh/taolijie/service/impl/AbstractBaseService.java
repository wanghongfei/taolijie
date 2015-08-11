package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.BaseMapper;
import com.fh.taolijie.service.BaseService;

/**
 * 继承此类可以获得基本的CRUD操作
 * Created by whf on 8/11/15.
 */
public abstract class AbstractBaseService<T> implements BaseService<T> {
    @Override
    public int add(T example) {
        return getMapper().add(example);
    }

    @Override
    public boolean updateByIdSelective(T example) {
        int rows = getMapper().updateByIdSelective(example);

        return rows > 0 ? true : false;
    }

    @Override
    public boolean deleteById(Integer id) {
        int rows = getMapper().deleteById(id);

        return rows > 0 ? true : false;
    }

    @Override
    public T findById(Integer id) {
        return getMapper().findById(id);
    }

    protected abstract BaseMapper<T> getMapper();

}
