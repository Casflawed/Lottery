package com.flameking.lottery.rpc;

import com.flameking.lottery.rpc.dto.ActivityDto;
import com.flameking.lottery.rpc.req.ActivityReq;
import com.flameking.lottery.rpc.res.ActivityRes;

/**
 * 活动配置服务层接口
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
public interface IActivityBooth {

//     IPage<Activity> getPage(ActivityQuery query);

     /**
      * 活动展台
      * 1. 创建活动
      * 2. 更新活动
      * 3. 查询活动
      *
      * @param req
      * @return ActivityRes
      */
     ActivityRes queryActivityById(ActivityReq req);

     Long create(ActivityDto activityDto);
//
//     boolean update(Activity activity);
//
//     boolean del(String id);

}

