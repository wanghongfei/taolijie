package com.fh.taolijie.service.v2;

/**
 * Created by wanghongfei on 15-6-10.
 */
public interface CreditsService {
    /**
     * 加分
     * @param memId 当前用户的id
     * @param addValue 要加的分值
     * @return 返回的DTO对象中只有两个字段是有效的, {@code name} 和 {@code currentCredits}.
     * 前者表示加完分之后积分的等级名称, 后者表示加完分之后的分值.
     */
    //CreditsRuleDto addCredits(Integer memId, Integer addValue);

    /**
     * 减分
     * @param memId 当前用户的id
     * @param deduceValue 要减少的分值
     * @return 返回的DTO对象中只有两个字段是有效的, {@code name} 和 {@code currentCredits}.
     * 前者表示加完分之后积分的等级名称, 后者表示加完分之后的分值.
     */
    //CredMo deduceCredits(Integer memId, Integer deduceValue);
}
