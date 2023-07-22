package com.flameking.lottery.domain.strategy.model;

import com.flameking.lottery.infrastructure.entity.Strategy;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRich {
    // 策略ID
    private Long strategyId;
    // 策略配置
    private Strategy strategy;
    // 策略明细
    private List<StrategyDetail> strategyDetailList;
}
