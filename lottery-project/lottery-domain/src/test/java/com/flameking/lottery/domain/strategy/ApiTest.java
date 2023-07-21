package com.flameking.lottery.domain.strategy;

import com.flameking.lottery.domain.strategy.algorithm.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.factory.LotteryStrategyFactory;
import com.flameking.lottery.infrastructure.entity.Strategy;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import com.flameking.lottery.infrastructure.service.IStrategyDetailService;
import com.flameking.lottery.infrastructure.service.IStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
  @Autowired
  private IStrategyService strategyService;

  @Test
  public void test_process() {
    Long strategyId = 1001L;
    //查询与策略ID关联的奖品ID
    List<StrategyDetail> awardInfos = strategyDetailService.getStrategyDetailsByStrategyId(strategyId);

    //获取该策略对应的抽奖策略
    Strategy strategy = strategyService.getByStrategyId(strategyId);
    ILotteryStrategy lotteryStrategy = LotteryStrategyFactory.getLotteryStrategy(strategy.getStrategyMode());

    //抽奖策略是否初始化
    lotteryStrategy.checkAndInitStrategy(strategyId, awardInfos);

    //抽奖
    Long awardId = lotteryStrategy.draw(strategyId);

    System.out.println(awardId);
  }

}
