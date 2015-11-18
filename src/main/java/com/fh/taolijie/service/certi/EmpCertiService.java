package com.fh.taolijie.service.certi;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.domain.certi.EmpCertiModel;

import java.util.List;


/**
 * 商家认证业务接口
 * Created by whf on 9/22/15.
 */
public interface EmpCertiService {
    /**
     * 申请认证
     * @param model
     */
    void addApplication(EmpCertiModel model);

    /**
     * 更新认证状态
     * @param certiId
     * @param memId 被认证用户的id
     * @param status
     * @param memo
     */
    void updateStatus(Integer certiId, Integer memId, CertiStatus status, String memo);

    /**
     * 检查是否已经认证
     * @param memId
     * @return
     */
    boolean checkVerified(Integer memId);

    EmpCertiModel findById(Integer certiId);

    ListResult<EmpCertiModel> findByStatus(CertiStatus status, int pn, int ps);

    /**
     * 查找商家用户的认证申请记录
     * @param memId
     * @return
     */
    ListResult<EmpCertiModel> findByMember(Integer memId);
}
