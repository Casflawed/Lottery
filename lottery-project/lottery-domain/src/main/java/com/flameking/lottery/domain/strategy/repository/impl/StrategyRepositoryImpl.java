package com.flameking.lottery.domain.strategy.repository.impl;

import com.flameking.lottery.domain.strategy.model.StrategyRich;
import com.flameking.lottery.domain.strategy.repository.IStrategyRepository;
import com.flameking.lottery.infrastructure.entity.Award;
import com.flameking.lottery.infrastructure.entity.Strategy;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import com.flameking.lottery.infrastructure.service.IAwardService;
import com.flameking.lottery.infrastructure.service.IStrategyDetailService;
import com.flameking.lottery.infrastructure.service.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StrategyRepositoryImpl implements IStrategyRepository {
    @Autowired
    private IStrategyDetailService strategyDetailService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IAwardService awardService;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        List<StrategyDetail> strategyDetails = strategyDetailService.getStrategyDetailsByStrategyId(strategyId);
        Strategy strategy = strategyService.getByStrategyId(strategyId);
        return new StrategyRich(strategyId, strategy, strategyDetails);
    }

    @Override
    public Award queryAward(Long awardId) {
        return awardService.getAwardByAwardId(awardId);
    }

    @Override
    public List<Long> queryAwardIdsWithoutAmount(Long strategyId) {
        return strategyDetailService.queryAwardIdsWithoutAmount(strategyId);
    }

    @Override
    public boolean decreaseLeftAwardCount(Long strategyId, Long awardId) {
        return strategyDetailService.decreaseLeftAwardCount(strategyId, awardId);
    }
}
