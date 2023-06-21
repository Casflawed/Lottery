package com.flameking.lottery.interfaces.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.infrastructure.entity.Activity;
import com.flameking.lottery.infrastructure.mapper.ActivityMapper;
import com.flameking.lottery.rpc.IActivityBooth;
import com.flameking.lottery.rpc.dto.ActivityDto;
import com.flameking.lottery.rpc.req.ActivityReq;
import com.flameking.lottery.rpc.res.ActivityRes;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;


/**
 * 活动配置服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
@Service
public class ActivityBooth extends ServiceImpl<ActivityMapper, Activity> implements IActivityBooth {

    @Resource
    private ActivityMapper activityMapper;

    @Override
    public ActivityRes queryActivityById(ActivityReq req) {

        Activity activity = getOne(new LambdaQueryWrapper<Activity>().eq(Activity::getActivityId, req.getActivityId()));

        ActivityDto activityDto = new ActivityDto();
        BeanUtils.copyProperties(activity,activityDto);

//        activityDto.setActivityId(activity.getActivityId());
//        activityDto.setActivityName(activity.getActivityName());
//        activityDto.setActivityDesc(activity.getActivityDesc());
//        activityDto.setBeginDateTime(activity.getBeginDateTime());
//        activityDto.setEndDateTime(activity.getEndDateTime());
//        activityDto.setStockCount(activity.getStockCount());
//        activityDto.setTakeCount(activity.getTakeCount());

        return new ActivityRes(new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), activityDto);
    }

    @Override
    public Long create(ActivityDto activityDto) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDto, activity);
        save(activity);
        return activity.getActivityId();
    }

    //    @Override
//    public IPage<Activity> getPage(ActivityQuery query){
//        Page<Activity> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }
//
//    @Override
//    public Activity findById(String id) {
//        return getById(id);
//    }
//
//    @Override
//    public Long create(Activity activity) {
//        activity.setCreator("wangwei");
//        activity.setCreateTime(LocalDateTime.now());
//        save(activity);
//        return activity.getId();
//    }
//
//    @Override
//    public boolean update(Activity activity) {
//        return updateById(activity);
//    }
//
//    @Override
//    public boolean del(String id) {
//        return removeById(id);
//    }

}

