package com.flameking.lottery.domain.strategy.impl;

import com.flameking.lottery.domain.strategy.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.entity.StrategyDetail;
import com.flameking.lottery.domain.strategy.service.IStrategyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单项概率抽奖算法
 *
 * @author WangWei
 * @dateTime 2023/7/19 21:58
 */
@Component
public class SingleProbabilityLotteryStrategy implements ILotteryStrategy {
  public final static int HASH_INCREMENT = 0x61c88647;

  @Autowired
  private IStrategyDetailService strategyDetailService;

  @Override
  public Long process(Long strategyId, int lotteryCode) {
    //查询与策略ID关联的奖品ID
    List<StrategyDetail> strategyDetails = strategyDetailService.getStrategyDetailsByStrategyId(strategyId);
    //将概率和奖品id用map存储
    HashMap<Long, Double> rateToAwardIdMap = new HashMap<>();
    strategyDetails.forEach(strategyDetail -> {
      rateToAwardIdMap.put(strategyDetail.getAwardId(), strategyDetail.getAwardRate());
    });
    //奖品池
    Map<Integer, Long> awardPoolMap = new HashMap<>();

    //初始化奖品池
    int lRange = 0;
    for (Map.Entry<Long, Double> entry : rateToAwardIdMap.entrySet()) {
      int rRange = (int) (entry.getValue() * 100) + lRange;
      for (int i = lRange + 1; i <= rRange; i++) {
        int hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
        Integer idx = hashCode & (128 - 1);
        awardPoolMap.put(idx, entry.getKey());
      }
      lRange = rRange;
    }

    return awardPoolMap.get(lotteryCode);
  }

  public static void main(String[] args) {

  }

}
