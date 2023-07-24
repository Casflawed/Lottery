package com.flameking.lottery.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 抽取的奖品信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DrawAwardInfo {

    /**
     * 奖品ID
     */
    private Long awardId;

    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private Integer awardType;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品内容「描述、奖品码、sku」
     */
    private String awardContent;
}
