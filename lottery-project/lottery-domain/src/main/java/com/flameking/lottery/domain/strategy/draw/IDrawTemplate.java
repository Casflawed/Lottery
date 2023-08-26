package com.flameking.lottery.domain.strategy.draw;

import com.flameking.lottery.domain.strategy.model.req.DrawReq;
import com.flameking.lottery.domain.strategy.model.res.DrawResult;

public interface IDrawTemplate {

    /**
     * 参与活动的用户进行抽奖
     */
    DrawResult doDraw(DrawReq req);
}
