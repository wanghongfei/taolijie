package com.fh.taolijie.dao.mapper;

/**
 * Created by whf on 8/11/15.
 */
public interface BaseMapper<T> {
    public int add(T example);

    public int updateByIdSelective(T example);

    public int deleteById(Integer id);

    public T findById(Integer id);
}
