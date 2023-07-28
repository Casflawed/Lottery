package com.flameking.lottery.domain.strategy.algorithm.impl;

import com.flameking.lottery.domain.strategy.algorithm.BaseRandomDrawAlgorithm;
import com.flameking.lottery.domain.strategy.model.vo.AwardRateInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 单项概率抽奖算法
 *
 * @author WangWei
 * @dateTime 2023/7/19 21:58
 */
@Slf4j
public class SingleRateRandomDrawAlgorithm extends BaseRandomDrawAlgorithm {
    //奖品池
    private final Map<Long, Long[]> singleAwardRatePool = new HashMap<>();

    @Override
    public Long draw(Long strategyId, List<Long> excludedAwardIds) {
        Long[] rateArr = singleAwardRatePool.get(strategyId);

        //生成随机码
        int randomCode = new Random().nextInt(TOTAL_RATE) + 1;
        log.debug("randomCode" + " == {}", randomCode);

        //散列随机码得到下标
        Integer idx = hashIdx(randomCode);
        Long awardId = rateArr[idx];

        //排除的奖品
        if (excludedAwardIds.contains(awardId)){
            return null;
        }

        //返回奖品id
        return awardId;
    }

    /**
     * 初始化单项概率的配置信息
     *
     * @param strategyId 策略id
     */
    public void initAwardRatePool(Long strategyId){
        //奖品池已初始化
        if (singleAwardRatePool.containsKey(strategyId)) {
            return;
        }

        //获取奖品概率信息
        List<AwardRateInfo> rateInfos = commonAwardRateInfos.get(strategyId);

        //创建该策略下的奖品池
        Long[] rateArr = singleAwardRatePool.computeIfAbsent(strategyId, key -> new Long[ARRAY_SIZE]);

        //初始化奖品池：使用散列函数将奖品信息平均发散
        int lRange = 0;
        for (AwardRateInfo awardInfo : rateInfos) {
            int rateValue = (int) (awardInfo.getAwardRate() * TOTAL_RATE);
            //右范围
            int rRange = lRange + rateValue;
            for (int i = lRange + 1; i <= rRange; i++) {
                Integer idx = hashIdx(i);
                rateArr[idx] = awardInfo.getAwardId();
            }
            //更新左范围
            lRange = rRange;
        }

        //内存持久化单项概率抽奖策略
        singleAwardRatePool.put(strategyId, rateArr);
    }

}
