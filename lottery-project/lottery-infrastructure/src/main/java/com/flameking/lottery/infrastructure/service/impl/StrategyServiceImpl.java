package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.Strategy;

import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.StrategyMapper;
import com.flameking.lottery.infrastructure.service.IStrategyService;
import org.springframework.stereotype.Service;


/**
 * 策略配置服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-07-21 15:12:54
 */
@Service
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements IStrategyService {

    @Resource
    private StrategyMapper strategyMapper;

    @Override
    public Strategy findById(String id) {
        return getById(id);
    }

    @Override
    public Long create(Strategy strategy) {
        save(strategy);
        return strategy.getId();
    }

    @Override
    public boolean update(Strategy strategy) {
        return updateById(strategy);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

    @Override
    public Strategy getByStrategyId(Long strategyId) {
        Strategy one = getOne(new LambdaQueryWrapper<Strategy>().eq(Strategy::getStrategyId, strategyId));
        return one;
    }

}

