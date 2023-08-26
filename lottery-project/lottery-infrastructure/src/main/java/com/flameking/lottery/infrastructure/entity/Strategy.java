package com.flameking.lottery.infrastructure.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 策略配置实体类
 *
 * @author WangWei
 * @dateTime 2023-07-21 15:12:54
 */
@Data
public class Strategy {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 策略描述
     */
    private String strategyDesc;

    /**
     * 策略方式「1:单项概率、2:总体概率」
     */
    private Integer strategyMode;

    /**
     * 发放奖品方式「1:即时、2:定时[含活动结束]、3:人工」
     */
    private Integer grantType;

    /**
     * 发放奖品时间
     */
    private Date grantDate;

    /**
     * 扩展信息
     */
    private String extInfo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
