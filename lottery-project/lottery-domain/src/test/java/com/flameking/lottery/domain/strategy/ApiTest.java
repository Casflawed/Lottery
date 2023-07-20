package com.flameking.lottery.domain.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
  private ILotteryStrategy lotteryStrategy;

  @Test
  public void test_process() {
    int lotteryCode = new Random().nextInt(100) + 1;
    Long awardId = lotteryStrategy.process(1001L, lotteryCode);
    System.out.println(awardId);
  }

}
