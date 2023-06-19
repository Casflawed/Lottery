package com.flameking.lottery.rpc;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.flameking.lottery.rpc.mapper"})
public class LotteryRpcApplication {

  public static void main(String[] args) {
    SpringApplication.run(LotteryRpcApplication.class, args);
  }

}
