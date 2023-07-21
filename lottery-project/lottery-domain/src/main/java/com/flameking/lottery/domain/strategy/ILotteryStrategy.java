package com.flameking.lottery.domain.strategy;

/**
 * 抽奖策略接口
 *
 * @author WangWei
 * @dateTime 2023/7/19 21:59
 */
public interface ILotteryStrategy {
  /**
   * 抽奖
   */
  Long draw();
}
