package com.flameking.lottery.interfaces;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.flameking.lottery"})
@MapperScan({"com.flameking.lottery.infrastructure.mapper"})
public class LotteryInterfacesApplication {

  public static void main(String[] args) {
    SpringApplication.run(LotteryInterfacesApplication.class, args);
  }

}
