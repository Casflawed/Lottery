package com.flameking.lottery.domain.rule.service.logic.impl;

import com.flameking.lottery.domain.rule.model.req.DecisionMatterReq;
import com.flameking.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * 性别规则
 */
@Component
public class UserGenderFilter extends BaseLogic {

    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }
    
}
