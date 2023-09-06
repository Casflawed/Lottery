package com.flameking.lottery.infrastructure.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.infrastructure.entity.Activity;

import java.util.List;

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

    /**
     * 扫描待处理的活动列表，状态为：通过、运行中
     *
     * @param id ID
     * @return 待处理的活动集合
     */
    List<Activity> scanToDoActivityList(Long id);

    /**
     * 更新用户领取活动后，活动库存
     *
     * @param activity  入参
     */
    boolean updateActivityStock(Activity activity);

}
