package com.flameking.lottery.infrastructure.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.infrastructure.entity.Activity;
import com.flameking.lottery.infrastructure.mapper.ActivityMapper;
import com.flameking.lottery.infrastructure.service.IActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public boolean updateActivityStock(Activity activity) {
        return update(new LambdaUpdateWrapper<Activity>()
                .set(Activity::getStockSurplusCount, activity.getStockSurplusCount())
                .eq(Activity::getActivityId, activity.getActivityId())
                .gt(Activity::getStockSurplusCount, activity.getStockSurplusCount()));
    }

    @Override
    public List<Activity> scanToDoActivityList(Long id) {
        return list(new LambdaQueryWrapper<Activity>()
                .ge(Activity::getId, id)
                .in(Activity::getState, Constants.ActivityState.PASS.getCode(), Constants.ActivityState.DOING.getCode())
                .orderByAsc(Activity::getId)
                // TODO: 2023/9/4 limit10存在一个问题，即数据库中是否存在data-n < data-10，
                //  按理数据库剩余的数据 data-x >= data-10，如果不是这样，数据库的某些数据这个方法永远扫描不到
                .last("LIMIT 10"));
    }
}
