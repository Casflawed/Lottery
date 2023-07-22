package com.flameking.lottery.domain.strategy.factory;

import com.flameking.lottery.domain.strategy.algorithm.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.algorithm.impl.EntiretyRateRandomDrawAlgorithm;
import com.flameking.lottery.domain.strategy.algorithm.impl.SingleProbabilityLotteryStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽奖策略静态工厂类
 */
public class LotteryStrategyFactory {
    public final static Map<Integer, ILotteryStrategy> lotteryStrategyPool = new HashMap<>();

    static {
        lotteryStrategyPool.put(1, new SingleProbabilityLotteryStrategy());
        lotteryStrategyPool.put(2, new EntiretyRateRandomDrawAlgorithm());
    }
    public static ILotteryStrategy getLotteryStrategy(int strategyMode){
        return lotteryStrategyPool.get(strategyMode);
    }
}
