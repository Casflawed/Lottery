package com.flameking.lottery.domain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.flameking.lottery.domain.strategy.mapper")
public class LotteryDomainApplication {

  public static void main(String[] args) {
    SpringApplication.run(LotteryDomainApplication.class, args);
  }

}
