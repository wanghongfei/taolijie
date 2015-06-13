package com.fh.taolijie.service.v2;

import com.fh.taolijie.domain.MemberVerifyRelModel;
import com.fh.taolijie.utils.Constants;

/**
 * Created by wanghongfei on 15-6-10.
 */
public interface MemberVerifyService {
    /**
     * 上传证件照片.
     *
     */
    void applyVerification(MemberVerifyRelModel model);

    /**
     * 批准通过个人认证
     * @param memberId
     */
    void approveVerification(Integer memberId);

    /**
     * 驳回认证
     * @param memberId
     */
    void rejectVerification(Integer memberId);

    MemberVerifyRelModel getVerificationByType(Integer memberId, Constants.VerificationType type);
}
