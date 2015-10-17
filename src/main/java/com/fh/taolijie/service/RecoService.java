package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.exception.checked.PostNotFound;

/**
 * 与推荐相关的业务接口
 * Created by whf on 10/15/15.
 */
public interface RecoService {
    ListResult<RecoPostModel> findBy(RecoPostModel example);

    int add(RecoPostModel model) throws PostNotFound;

    int update(RecoPostModel model);

    int delete(Integer id);
}
