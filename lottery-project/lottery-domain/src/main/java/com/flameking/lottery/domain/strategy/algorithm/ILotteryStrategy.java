package com.flameking.lottery.domain.strategy.algorithm;

import com.flameking.lottery.infrastructure.entity.StrategyDetail;

import java.util.List;

/**
 * 抽奖策略接口
 *
 * @author WangWei
 * @dateTime 2023/7/19 21:59
 */
public interface ILotteryStrategy {
    /**
     * 抽奖
     *
     * @param strategyId
     */
    Long draw(Long strategyId);

    /**
     * 检查抽奖策略是否初始化
     *
     * @param strategyId 策略id
     * @param awardInfos 奖品信息
     */
    void checkAndInitStrategy(Long strategyId, List<StrategyDetail> awardInfos);
}
