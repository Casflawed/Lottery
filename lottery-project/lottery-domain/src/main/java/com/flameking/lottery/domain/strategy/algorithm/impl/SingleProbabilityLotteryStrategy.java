package com.flameking.lottery.domain.strategy.algorithm.impl;

import com.flameking.lottery.domain.strategy.algorithm.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.algorithm.BaseLotteryStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 单项概率抽奖算法
 *
 * @author WangWei
 * @dateTime 2023/7/19 21:58
 */
@Slf4j
public class SingleProbabilityLotteryStrategy extends BaseLotteryStrategy implements ILotteryStrategy {
    @Override
    public Long draw(Long strategyId) {
        Long[] rateArr = awardPool.get(strategyId);
        //生成随机码
        int randomCode = new Random().nextInt(TOTAL_RATE) + 1;
        log.debug("randomCode" + " == {}", randomCode);
        //散列随机码得到下标
        Integer idx = hashIdx(randomCode);
        //返回奖品id
        return rateArr[idx];
    }

}
