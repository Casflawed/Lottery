package com.flameking.lottery.domain.strategy.algorithm;

import com.flameking.lottery.domain.strategy.model.AwardRateInfo;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseLotteryStrategy implements ILotteryStrategy {
    protected final static int TOTAL_RATE = 100;
    protected final static int ARRAY_SIZE = 128;
    private final static int HASH_INCREMENT = 0x61c88647;
    //奖品信息
    protected Map<Long, List<AwardRateInfo>> commonAwardRateInfos = new HashMap<>();

    @Override
    public void initAwardRateInfo(Long strategyId, List<StrategyDetail> strategyDetails){
        Assert.notEmpty(strategyDetails, "奖品概率信息为空");
        //奖品概率信息已初始化
        if (commonAwardRateInfos.containsKey(strategyId)){
            return;
        }
        List<AwardRateInfo> awardRateInfos = commonAwardRateInfos.computeIfAbsent(strategyId, key -> new ArrayList<>());
        //防止浅拷贝，dto 转换
        strategyDetails.forEach(strategyDetail -> awardRateInfos.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate())));
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
