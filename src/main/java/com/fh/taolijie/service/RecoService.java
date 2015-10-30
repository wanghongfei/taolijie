package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.RecoType;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.PostNotFoundException;
import com.fh.taolijie.exception.checked.RecoRepeatedException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;

/**
 * 与推荐相关的业务接口
 * Created by whf on 10/15/15.
 */
public interface RecoService {
    ListResult<RecoPostModel> findBy(RecoPostModel example);

    /**
     * 添加置顶推荐
     */
    int add(RecoPostModel model, Integer orderId)
            throws PostNotFoundException, RecoRepeatedException, CashAccNotExistsException, BalanceNotEnoughException, FinalStatusException, OrderNotFoundException, PermissionException;

    /**
     * 添加标签推荐.
     * @param postType 目前仅支持 RecoType.QUEST
     * @return
     */
    int addTag(Integer postId, RecoType postType, int hours)
            throws PostNotFoundException, BalanceNotEnoughException, CashAccNotExistsException;

    int update(RecoPostModel model);

    int delete(Integer id);
}
