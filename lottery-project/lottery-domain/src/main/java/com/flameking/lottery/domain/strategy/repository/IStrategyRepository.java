package com.flameking.lottery.domain.strategy.repository;

import com.flameking.lottery.domain.strategy.model.StrategyRich;
import com.flameking.lottery.infrastructure.entity.Award;

import java.util.List;

public interface IStrategyRepository {
    StrategyRich queryStrategyRich(Long strategyId);
    Award queryAward(Long awardId);
    List<Long> queryAwardIdsWithoutAmount(Long strategyId);
    boolean decreaseLeftAwardCount(Long strategyId, Long awardId);
}
