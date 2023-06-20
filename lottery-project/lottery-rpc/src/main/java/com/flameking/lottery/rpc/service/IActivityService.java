package com.flameking.lottery.rpc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flameking.lottery.rpc.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

import com.flameking.lottery.rpc.model.so.ActivityQuery;

/**
 * 活动配置服务层接口
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
public interface IActivityService extends IService<Activity> {

     IPage<Activity> getPage(ActivityQuery query);

     Activity findById(String id);

     Long create(Activity activity);

     boolean update(Activity activity);

     boolean del(String id);

}

