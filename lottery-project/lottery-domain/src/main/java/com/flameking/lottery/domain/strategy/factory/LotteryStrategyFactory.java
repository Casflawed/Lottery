package com.flameking.lottery.domain.strategy.factory;

import com.flameking.lottery.domain.strategy.algorithm.IRandomDrawAlgorithm;
import com.flameking.lottery.domain.strategy.algorithm.impl.EntiretyRateRandomDrawAlgorithm;
import com.flameking.lottery.domain.strategy.algorithm.impl.SingleRateRandomDrawAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽奖策略静态工厂类
 */
public class LotteryStrategyFactory {
    public final static Map<Integer, IRandomDrawAlgorithm> lotteryStrategyPool = new HashMap<>();

    static {
        lotteryStrategyPool.put(1, new SingleRateRandomDrawAlgorithm());
        lotteryStrategyPool.put(2, new EntiretyRateRandomDrawAlgorithm());
    }
    public static IRandomDrawAlgorithm getLotteryStrategy(int strategyMode){
        return lotteryStrategyPool.get(strategyMode);
    }
}
