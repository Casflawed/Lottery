package com.flameking.lottery.infrastructure.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.infrastructure.entity.Activity;

public interface IActivityService extends IService<Activity> {

    /**
     * 变更活动状态
     *
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @param transferState 转换后状态
     */
    boolean alterState(Long activityId, Constants.ActivityState currentState, Constants.ActivityState transferState);

    Activity queryActivityById(Long activityId);

    boolean subtractionActivityStock(Long activityId);
}
