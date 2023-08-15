package com.flameking.lottery.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户活动参与次数实体类
 *
 * @author WangWei
 * @dateTime 2023-08-15 21:20:14
 */
@Data
public class UserTakeActivityCount{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户id */
    private String uId;

    /** 活动id */
    private Long activityId;

    /** 可领取次数 */
    private Integer totalCount;

    /** 剩余领取次数 */
    private Integer leftCount;

    /**  */
    private LocalDateTime createTime;

    /**  */
    private LocalDateTime updateTime;


}
