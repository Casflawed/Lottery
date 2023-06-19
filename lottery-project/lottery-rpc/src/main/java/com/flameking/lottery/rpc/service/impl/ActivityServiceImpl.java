package com.flameking.lottery.rpc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.flameking.lottery.rpc.entity.Activity;
import javax.annotation.Resource;

import com.flameking.lottery.rpc.mapper.ActivityMapper;
import com.flameking.lottery.rpc.model.so.ActivityQuery;
import com.flameking.lottery.rpc.service.IActivityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 活动配置服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Resource
    private ActivityMapper activityMapper;


    @Override
    public IPage<Activity> getPage(ActivityQuery query){
        Page<Activity> page = new Page<>(query.getPageNum(),query.getPageSize());
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        //需要根据业务加上对应的条件
        return page(page, wrapper);
    }

    @Override
    public Activity findById(String id) {
        return getById(id);
    }

    @Override
    public Long create(Activity activity) {
        activity.setCreator("wangwei");
        activity.setCreateTime(LocalDateTime.now());
        save(activity);
        return activity.getId();
    }

    @Override
    public boolean update(Activity activity) {
        return updateById(activity);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

}

