package com.flameking.lottery.domain.strategy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 奖品概率信息，奖品编号、库存、概率
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardRateInfo {
    // 奖品ID
    private Long awardId;

    // 中奖概率
    private Double awardRate;
}
