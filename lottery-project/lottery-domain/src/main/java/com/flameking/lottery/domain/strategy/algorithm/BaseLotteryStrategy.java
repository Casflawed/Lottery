package com.flameking.lottery.domain.strategy.algorithm;

import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseLotteryStrategy implements ILotteryStrategy {
    protected final static int TOTAL_RATE = 100;
    private final static int ARRAY_SIZE = 128;
    private final static int HASH_INCREMENT = 0x61c88647;
    //奖品池，strategyId -> 奖品和概率
    protected Map<Long, Long[]> awardPool;

    /**
     * 检查抽奖策略是否初始化
     *
     * @param strategyId 策略id
     * @param awardInfos 奖品信息
     */
    public void checkAndInitStrategy(Long strategyId, List<StrategyDetail> awardInfos){
        //已初始化
        if (awardPool.containsKey(strategyId)) {
            return;
        }

        Assert.notEmpty(awardInfos, "奖品信息为空");

        awardPool = new HashMap<>();
        Long[] rateArr = awardPool.computeIfAbsent(strategyId, key -> new Long[ARRAY_SIZE]);

        //初始化奖品池：使用散列函数将抽奖概率平均发散
        int lRange = 0;
        for (StrategyDetail awardInfo : awardInfos) {
            int rateValue = (int) (awardInfo.getAwardRate() * TOTAL_RATE);
            for (int i = lRange + 1; i <= lRange + rateValue; i++) {
                Integer idx = hashIdx(i);
                rateArr[idx] = awardInfo.getAwardId();
            }
            lRange += rateValue;
        }

        //本地持久化抽奖策略
        awardPool.put(strategyId, rateArr);
    }

    /**
     * 黄金比例乘法散列函数
     *
     * @param randomCode 随机码
     */
    protected Integer hashIdx(int randomCode) {
        int hashCode = randomCode * HASH_INCREMENT + HASH_INCREMENT;
        return hashCode & (ARRAY_SIZE - 1);
    }
}
