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
     * @param strategyId       策略id
     * @param excludedAwardIds 排除掉的奖品id
     */
    String draw(Long strategyId, List<Long> excludedAwardIds);

    /**
     * 检查抽奖策略是否初始化
     *
     * @param strategyId      策略id
     * @param strategyDetails 策略详情信息
     */
    void checkAndInitStrategy(Long strategyId, List<StrategyDetail> strategyDetails);

    /**
     * 初始化单项概率抽奖策略
     *
     * @param strategyId 策略id
     */
    void init(Long strategyId);
}
