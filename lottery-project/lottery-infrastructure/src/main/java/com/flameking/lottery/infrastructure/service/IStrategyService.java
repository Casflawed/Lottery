package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.Strategy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 策略配置服务层接口
 *
 * @author WangWei
 * @dateTime 2023-07-21 15:12:54
 */
public interface IStrategyService extends IService<Strategy> {

     Strategy findById(String id);

     Long create(Strategy strategy);

     boolean update(Strategy strategy);

     boolean del(String id);

     Strategy getByStrategyId(Long strategyId);
}

