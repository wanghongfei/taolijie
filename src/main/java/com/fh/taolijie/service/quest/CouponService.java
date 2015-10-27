package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.CouponModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.quest.*;

/**
 * Created by whf on 10/27/15.
 */
public interface CouponService {
    /**
     * 商家创建coupon
     * @param model
     * @return
     */
    int add(CouponModel model, QuestModel quest);

    /**
     * 领取一个coupon
     * @param questId
     * @return
     */
    CouponModel acquireCoupon(Integer questId, Integer memId)
            throws NotEnoughCouponException, HackException;

    /**
     * 商家验证coupon code
     * @param code
     * @return
     */
    boolean validateCoupon(String code)
            throws CouponNotFoundException, InvalidCouponException, CouponUsedException, CouponExpiredException;

    ListResult<CouponModel> findBy(CouponModel model);
}
