package com.flameking.lottery.domain.strategy.draw;

import com.flameking.lottery.domain.strategy.algorithm.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.factory.LotteryStrategyFactory;
import com.flameking.lottery.domain.strategy.model.StrategyRich;
import com.flameking.lottery.infrastructure.entity.Award;
import com.flameking.lottery.infrastructure.entity.Strategy;

import java.util.List;

public abstract class BaseDrawTemplate extends DrawStrategySupport implements IDrawTemplate{


    @Override
    public Award doDraw(Long uId, Long strategyId) {
        //查询奖品概率和策略配置信息
        StrategyRich strategyRich = this.queryStrategyRich(strategyId);
        Strategy strategy = strategyRich.getStrategy();

        //初始化抽奖策略
        this.initDrawStrategy(strategy.getStrategyMode(), strategyRich);

        //查询不在抽奖范围内的奖品
        List<Long> excludeAwardIds = this.queryExcludeAwardIds(strategyId);

        //执行抽奖
        Long awardId = this.excDrawStrategy(strategyId, strategy.getStrategyMode(), excludeAwardIds);

        //包装奖品信息
        return awardId == null ? null : this.queryAward(awardId);
    }

    /**
     * 初始化抽奖策略
     *
     * @param strategyMode
     * @param strategyRich
     */
    private void initDrawStrategy(Integer strategyMode, StrategyRich strategyRich) {
        ILotteryStrategy lotteryStrategy = LotteryStrategyFactory.getLotteryStrategy(strategyMode);
        lotteryStrategy.initAwardRateInfo(strategyRich.getStrategyId(), strategyRich.getStrategyDetailList());
    }

    /**
     * 执行抽奖策略
     *
     * @param strategyId
     * @param strategyMode
     * @param excludeAwardIds
     */
    protected abstract Long excDrawStrategy(Long strategyId, Integer strategyMode, List<Long> excludeAwardIds);

    /**
     * 查询查询不在抽奖范围内的奖品
     *
     * @param strategyId
     */
    protected abstract List<Long> queryExcludeAwardIds(Long strategyId);
}
