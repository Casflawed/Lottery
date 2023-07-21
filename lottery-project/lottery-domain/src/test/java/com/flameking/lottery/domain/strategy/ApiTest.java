package com.flameking.lottery.domain.strategy;

import com.flameking.lottery.domain.strategy.entity.StrategyDetail;
import com.flameking.lottery.domain.strategy.impl.SingleProbabilityLotteryStrategy;
import com.flameking.lottery.domain.strategy.service.IStrategyDetailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author WangWei
 * @dateTime 2023/6/20 16:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiTest {
  @Autowired
  private IStrategyDetailService strategyDetailService;

  @Test
  public void test_process() {
    //查询与策略ID关联的奖品ID
    List<StrategyDetail> strategyDetails = strategyDetailService.getStrategyDetailsByStrategyId(1001L);

    //将概率和奖品id用map存储
    HashMap<Long, Double> rateToAwardIdMap = new HashMap<>();
    strategyDetails.forEach(strategyDetail -> {
      rateToAwardIdMap.put(strategyDetail.getAwardId(), strategyDetail.getAwardRate());
    });

    //根据抽奖策略并抽奖
    ILotteryStrategy lotteryStrategy = new SingleProbabilityLotteryStrategy(rateToAwardIdMap);
    Long awardId = lotteryStrategy.draw();

    System.out.println(awardId);
  }

}
