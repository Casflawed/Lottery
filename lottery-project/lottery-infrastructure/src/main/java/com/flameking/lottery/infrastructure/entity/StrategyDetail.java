package com.flameking.lottery.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 策略明细实体类
 *
 * @author WangWei
 * @dateTime 2023-07-19 22:20:52
 */
@Data
public class StrategyDetail{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 策略ID */
    private Long strategyId;

    /** 奖品ID */
    private Long awardId;

    /** 奖品数量 */
    private Integer awardCount;

    /** 奖品剩余数量 */
    private Integer awardLeftCount;

    /** 中奖概率 */
    private Double awardRate;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 修改时间 */
    private LocalDateTime updateTime;


}
