package com.flameking.lottery.domain.strategy.draw;

import com.flameking.lottery.infrastructure.entity.Award;

public interface IDrawTemplate {

    /**
     * 参与活动的用户进行抽奖
     *
     * @param uId        用户id
     * @param strategyId 策略id
     */
    Award doDraw(Long uId, Long strategyId);
}
