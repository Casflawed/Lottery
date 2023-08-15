package com.flameking.lottery.infrastructure.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.infrastructure.entity.Activity;
import com.flameking.lottery.infrastructure.mapper.ActivityMapper;
import com.flameking.lottery.infrastructure.service.IActivityService;
import org.springframework.stereotype.Service;

/**
 * 活动配置服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-07-21 15:12:54
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Override
    public boolean alterState(Long activityId, Constants.ActivityState currentState, Constants.ActivityState transferState) {
        LambdaUpdateWrapper<Activity> updateWrapper = new LambdaUpdateWrapper<>();
        //数据库行锁，解决并发问题
        updateWrapper.set(Activity::getState, transferState.getCode())
                .eq(Activity::getActivityId, activityId)
                .eq(Activity::getState, currentState.getCode());
        return update(updateWrapper);
    }

    @Override
    public Activity queryActivityById(Long activityId) {
        return getOne(new LambdaQueryWrapper<Activity>()
                .eq(Activity::getActivityId, activityId));
    }

    @Override
    public boolean subtractionActivityStock(Long activityId) {
        LambdaUpdateWrapper<Activity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.setSql("stock_surplus_count = stock_surplus_count - 1")
                .eq(Activity::getActivityId, activityId)
                .gt(Activity::getStockSurplusCount, 0);
        return update(updateWrapper);
    }
}
