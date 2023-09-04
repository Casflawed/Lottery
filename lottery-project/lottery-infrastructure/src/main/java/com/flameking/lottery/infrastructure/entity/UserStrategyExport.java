package com.flameking.lottery.infrastructure.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户策略计算结果实体类
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
@Data
public class UserStrategyExport {
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户id */
    private String uId;

    /** 活动id */
    private Long activityId;

    /** 订单id */
    private Long orderId;

    /** 策略id */
    private Long strategyId;

    /** 策略方式（1.单项概率；2.总体概率） */
    private Integer strategyMode;

    /** 发放奖品方式（1.即时；2.定时[含活动结束]；3.人工） */
    private Integer grantType;

    /** 发奖时间 */
    private Date grantDate;

    /** 发奖状态 */
    private Integer grantState;

    /** 发奖id */
    private Long awardId;

    /** 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品） */
    private Integer awardType;

    /** 奖品名称 */
    private String awardName;

    /** 奖品内容「文字描述、Key、码」 */
    private String awardContent;

    /** 防重ID */
    private String uuid;

    /** 消息发送状态（0未发送、1发送成功、2发送失败） */
    private Integer mqState;

    /**  */
    private LocalDateTime createTime;

    /**  */
    private LocalDateTime updateTime;


}
