package com.flameking.lottery.domain.strategy.repository;

import com.flameking.lottery.domain.strategy.model.vo.StrategyRich;
import com.flameking.lottery.domain.strategy.model.vo.AwardBriefVO;

import java.util.List;

public interface IStrategyRepository {
    StrategyRich queryStrategyRich(Long strategyId);
    AwardBriefVO queryAward(Long awardId);
    List<Long> queryAwardIdsWithoutAmount(Long strategyId);
    boolean decreaseLeftAwardCount(Long strategyId, Long awardId);
}
