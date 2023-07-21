package com.flameking.lottery.domain.strategy.draw.impl;

import com.flameking.lottery.domain.strategy.algorithm.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.factory.LotteryStrategyFactory;
import com.flameking.lottery.infrastructure.entity.Award;
import com.flameking.lottery.infrastructure.entity.Strategy;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import com.flameking.lottery.infrastructure.service.IAwardService;
import com.flameking.lottery.domain.strategy.draw.IDrawTemplate;
import com.flameking.lottery.infrastructure.service.IStrategyDetailService;
import com.flameking.lottery.infrastructure.service.IStrategyService;

import javax.annotation.Resource;
import java.util.List;

public class IDrawTemplateImpl implements IDrawTemplate {
    private ILotteryStrategy lotteryStrategy;
    @Resource
    private IStrategyDetailService strategyDetailService;
    @Resource
    private IStrategyService strategyService;
    @Resource
    private IAwardService awardService;

    /**
     * 抽奖策略是否初始化
     *
     * @param strategy
     * @param awardInfos
     */
    private void checkAndInitStrategy(Strategy strategy, List<StrategyDetail> awardInfos){
        Integer strategyMode = strategy.getStrategyMode();
        if (strategyMode != 1){
            return;
        }
        lotteryStrategy.checkAndInitStrategy(strategy.getStrategyId(), awardInfos);
    }

    @Override
    public Award doDraw(Long uId, Long strategyId) {
        //查询奖品信息和被排除的奖品信息
        List<StrategyDetail> awardInfos = strategyDetailService.getStrategyDetailsByStrategyId(strategyId);

        //根据 strategyId 查询策略信息
        Strategy strategy = strategyService.getByStrategyId(strategyId);

        //根据 strategyMode 查询策略配置
        lotteryStrategy = LotteryStrategyFactory.getLotteryStrategy(strategy.getStrategyMode());

        //检查单项概率抽奖策略是否初始化，非单项概率抽奖策略直接跳过
        checkAndInitStrategy(strategy, awardInfos);

        //执行抽奖
        Long awardId = lotteryStrategy.draw(strategyId);

        //包装奖品信息
        return awardService.getAwardByAwardId(awardId);
    }
}
