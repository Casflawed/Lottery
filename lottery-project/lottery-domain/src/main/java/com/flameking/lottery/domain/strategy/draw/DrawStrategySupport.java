package com.flameking.lottery.domain.strategy.draw;

import com.flameking.lottery.domain.strategy.model.vo.StrategyRich;
import com.flameking.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.flameking.lottery.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;
import java.util.List;

public class DrawStrategySupport {
    @Resource
    protected IStrategyRepository strategyRepository;

    protected StrategyRich queryStrategyRich(Long strategyId){
        return strategyRepository.queryStrategyRich(strategyId);
    }
    protected AwardBriefVO queryAward(Long awardId){
        return strategyRepository.queryAward(awardId);
    }
    protected List<Long> queryAwardIdsWithoutAmount(Long strategyId){
        return strategyRepository.queryAwardIdsWithoutAmount(strategyId);
    }
    protected boolean decreaseLeftAwardCount(Long strategyId, Long awardId){
        return strategyRepository.decreaseLeftAwardCount(strategyId, awardId);
    }

}
