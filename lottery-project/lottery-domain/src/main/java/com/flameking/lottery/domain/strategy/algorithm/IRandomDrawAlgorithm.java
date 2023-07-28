package com.flameking.lottery.domain.strategy.algorithm;

import com.flameking.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;

import java.util.List;

/**
 * 抽奖策略接口
 *
 * @author WangWei
 * @dateTime 2023/7/19 21:59
 */
public interface IRandomDrawAlgorithm {
    /**
     * 抽奖
     *
     * @param strategyId       策略id
     * @param excludedAwardIds 排除掉的奖品id
     */
    Long draw(Long strategyId, List<Long> excludedAwardIds);

    /**
     * 检查并初始化
     *
     * @param strategyId      策略id
     * @param strategyDetails 奖品概率信息
     */
    void initAwardRateInfo(Long strategyId, List<StrategyDetailBriefVO> strategyDetails);
}
