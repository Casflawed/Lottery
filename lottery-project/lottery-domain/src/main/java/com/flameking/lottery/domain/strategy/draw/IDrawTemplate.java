package com.flameking.lottery.domain.strategy.draw;

import com.flameking.lottery.domain.strategy.model.res.DrawResult;

public interface IDrawTemplate {

    /**
     * 参与活动的用户进行抽奖
     */
    DrawResult doDraw(Long uId, Long strategyId);
}
