package com.flameking.lottery.domain.award.template;

import com.flameking.lottery.domain.award.model.res.AwardSenderRes;

/**
 * 执行发奖流程类
 */
public interface IAwardSenderTemplate {

    /**
     * 发奖/配送商品
     *
     * @param uId
     * @param strategyId
     * @param orderId
     */
    AwardSenderRes sendAward(String uId, Long strategyId, Long orderId);
}
