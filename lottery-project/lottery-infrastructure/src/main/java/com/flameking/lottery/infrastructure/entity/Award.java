package com.flameking.lottery.infrastructure.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 奖品配置实体类
 *
 * @author WangWei
 * @dateTime 2023-07-21 17:00:12
 */
@Data
public class Award {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 奖品ID
     */
    private Long awardId;

    /**
     * 奖品类型（文字描述、兑换码、优惠券、实物奖品暂无）
     */
    private Integer awardType;

    /**
     * 奖品数量
     */
    private Integer awardCount;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品内容「文字描述、Key、码」
     */
    private String awardContent;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
