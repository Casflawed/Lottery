package com.flameking.lottery.application.process;

import com.flameking.lottery.application.process.req.DrawProcessReq;
import com.flameking.lottery.application.process.res.DrawProcessResult;
import com.flameking.lottery.application.process.res.RuleQuantificationCrowdResult;
import com.flameking.lottery.domain.rule.model.req.DecisionMatterReq;

public interface IActivityProcess {
    /**
     * 抽奖活动流程编排
     *
     * @return
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);

    /**
     * 规则量化人群，返回可参与的活动ID
     * @param req   规则请求
     * @return      量化结果，用户可以参与的活动ID
     */
    RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req);

}
