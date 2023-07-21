package com.flameking.lottery.infrastructure.vo.request.borrower;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangWei
 * @dateTime 2023-07-19 22:20:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StrategyDetailReq{
    private Long id;

    private Long strategyId;

    private Long awardId;

    private Long awardCount;

    private BigDecimal awardRate;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
