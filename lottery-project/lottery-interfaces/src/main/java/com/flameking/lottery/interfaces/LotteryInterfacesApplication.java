package com.flameking.lottery.interfaces;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.flameking.lottery"})
@MapperScan({"com.flameking.lottery.infrastructure.mapper"})
public class LotteryInterfacesApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(LotteryInterfacesApplication.class, args);
    String[] beanDefinitionNames = context.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      System.out.println(beanDefinitionName);
    }
  }

}
