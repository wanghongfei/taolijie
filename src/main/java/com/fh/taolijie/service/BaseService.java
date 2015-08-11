package com.fh.taolijie.service;

/**
 * 所有与CRUD有关的service都应当实现该接口
 * Created by whf on 8/11/15.
 */
public interface BaseService<T> {
    int add(T example);

    boolean updateByIdSelective(T example);

    boolean deleteById(Integer id);

    T findById(Integer id);
}
