package com.flameking.lottery.domain.strategy.algorithm.impl;

import com.flameking.lottery.domain.strategy.algorithm.BaseLotteryStrategy;
import com.flameking.lottery.domain.strategy.algorithm.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.model.AwardRateInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 必中奖抽奖策略
 */
public class EntiretyRateRandomDrawAlgorithm extends BaseLotteryStrategy implements ILotteryStrategy {
    @Override
    public String draw(Long strategyId, List<Long> excludedAwardIds) {
        List<AwardRateInfo> rateInfos = awardInfos.get(strategyId);
        List<AwardRateInfo> tmpRateInfos = new ArrayList<>();

        //动态计算全概率
        Double leftRate = Double.valueOf("0");

        for (AwardRateInfo rateInfo : rateInfos) {
            if (!excludedAwardIds.contains(rateInfo.getAwardId())){
                leftRate += rateInfo.getAwardRate();
                //有效奖品
                tmpRateInfos.add(rateInfo);
            }
        }
        Long awardId = null;

        //有效奖品数量 == 0
        if (tmpRateInfos.size() == 0){
            return null;
        }
        //有效奖品数量 == 1
        if (tmpRateInfos.size() == 1){
            return String.valueOf(tmpRateInfos.get(0).getAwardId());
        }

        //获取随机概率值
        int randomVal = new Random().nextInt(100) + 1;

        //循环判断获取区间
        int cursorVal = 0;
        for (AwardRateInfo info : tmpRateInfos) {
            // TODO: 2023/7/22  做除法时会有进度损失，建议用BigDecimal
            int rateVal = (int) (info.getAwardRate() / leftRate * TOTAL_RATE);
            if (randomVal <= (cursorVal + rateVal)) {
                awardId = info.getAwardId();
                break;
            }
            cursorVal += rateVal;
        }

        // 返回中奖结果
        return String.valueOf(awardId);
    }
}
