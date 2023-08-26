package com.flameking.lottery.application.process.req;

import lombok.Data;

/**
 * 抽奖请求
 */
@Data
public class DrawProcessReq {

    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;

}
