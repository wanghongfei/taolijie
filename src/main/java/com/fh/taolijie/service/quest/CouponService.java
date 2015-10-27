package com.fh.taolijie.service.quest;

import com.fh.taolijie.domain.CouponModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;

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
}
