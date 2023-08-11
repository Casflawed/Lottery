package com.flameking.lottery.infrastructure.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户参与活动记录实体类
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
@Data
public class UserTakeActivity{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户ID */
    private String uId;

    /** 活动领取ID */
    private Long takeId;

    /** 活动ID */
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 活动领取时间 */
    private Date takeDate;

    /** 领取次数 */
    private Integer takeCount;

    /** 防重ID */
    private String uuid;

    /**  */
    private LocalDateTime createTime;

    /**  */
    private LocalDateTime updateTime;


}
