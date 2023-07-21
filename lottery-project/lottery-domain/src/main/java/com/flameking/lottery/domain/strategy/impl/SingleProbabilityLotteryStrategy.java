package com.flameking.lottery.domain.strategy.impl;

import com.flameking.lottery.domain.strategy.ILotteryStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 单项概率抽奖算法
 *
 * @author WangWei
 * @dateTime 2023/7/19 21:58
 */
@Slf4j
public class SingleProbabilityLotteryStrategy implements ILotteryStrategy {
  public final static int HASH_INCREMENT = 0x61c88647;
  public final static int TUPLE_SIZE = 100;
  public final static int MOD_SIZE = 128;
  //奖品池
  private Map<Integer, Long> awardPool;

  public SingleProbabilityLotteryStrategy(Map<Long, Double> rateToAwardIdMap) {
    initAwardPool(rateToAwardIdMap);
  }

  /**
   * 初始化奖品池
   */
  private void initAwardPool(Map<Long, Double> rateToAwardIdMap){
    int lRange = 0;
    awardPool = new HashMap<>();
    for (Map.Entry<Long, Double> entry : rateToAwardIdMap.entrySet()) {
      int rRange = (int) (entry.getValue() * TUPLE_SIZE) + lRange;
      for (int i = lRange + 1; i <= rRange; i++) {
        Integer idx = hashIdx(i);
        this.awardPool.put(idx, entry.getKey());
      }
      lRange = rRange;
    }
  }

  /**
   * 获取散列后的下标
   *
   * @param randomCode 随机码
   */
  private Integer hashIdx(int randomCode){
    int hashCode = randomCode * HASH_INCREMENT + HASH_INCREMENT;
    Integer idx = hashCode & (MOD_SIZE - 1);
    return idx;
  }

  @Override
  public Long draw() {
    //生成随机码
    int randomCode = new Random().nextInt(TUPLE_SIZE) + 1;
    log.debug("randomCode" + " == {}", randomCode);
    //散列随机码得到下标
    Integer idx = hashIdx(randomCode);
    //获得奖品id
    Long awardId = awardPool.get(idx);
    return awardId;
  }

}
