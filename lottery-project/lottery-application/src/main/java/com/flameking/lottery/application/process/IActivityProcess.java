package com.flameking.lottery.application.process;

import com.flameking.lottery.application.process.req.DrawProcessReq;
import com.flameking.lottery.application.process.res.DrawProcessResult;

public interface IActivityProcess {
    /**
     * 抽奖活动流程编排
     *
     * @return
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);
}
