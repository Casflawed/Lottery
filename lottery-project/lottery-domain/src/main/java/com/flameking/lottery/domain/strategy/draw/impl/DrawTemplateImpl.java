package com.flameking.lottery.domain.strategy.draw.impl;

import com.flameking.lottery.domain.strategy.algorithm.IRandomDrawAlgorithm;
import com.flameking.lottery.domain.strategy.algorithm.impl.SingleRateRandomDrawAlgorithm;
import com.flameking.lottery.domain.strategy.draw.BaseDrawTemplate;
import com.flameking.lottery.domain.strategy.factory.RandomDrawAlgorithmFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DrawTemplateImpl extends BaseDrawTemplate {
    @Override
    protected Long excRandomDrawAlgorithm(Long strategyId, Integer strategyMode, List<Long> excludeAwardIds) {
        IRandomDrawAlgorithm lotteryStrategy = RandomDrawAlgorithmFactory.getRandomDrawAlgorithm(strategyMode);
        Long awardId;
        //单项概率抽奖策略需要初始化奖品概率池
        if (strategyMode == 1){
            SingleRateRandomDrawAlgorithm singleRateRandomDrawAlgorithm = (SingleRateRandomDrawAlgorithm) lotteryStrategy;
            singleRateRandomDrawAlgorithm.initAwardRatePool(strategyId);
        }
        awardId = lotteryStrategy.draw(strategyId, excludeAwardIds);

        if (awardId != null){
            boolean isSuccess = this.decreaseLeftAwardCount(strategyId, awardId);
            //扣减失败，则等于未中奖
            if (!isSuccess){
                return null;
            }
        }
        return awardId;
    }

    @Override
    protected List<Long> queryExcludeAwardIds(Long strategyId) {
        return this.queryAwardIdsWithoutAmount(strategyId);
    }
}
