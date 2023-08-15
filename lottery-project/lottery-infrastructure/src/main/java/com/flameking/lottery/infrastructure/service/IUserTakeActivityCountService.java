package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.UserTakeActivityCount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户活动参与次数服务层接口
 *
 * @author WangWei
 * @dateTime 2023-08-15 21:20:14
 */
public interface IUserTakeActivityCountService extends IService<UserTakeActivityCount> {

//     IPage<UserTakeActivityCount> getPage(UserTakeActivityCountQuery query);

     UserTakeActivityCount findById(String id);

     Long create(UserTakeActivityCount userTakeActivityCount);

     boolean update(UserTakeActivityCount userTakeActivityCount);

     boolean del(String id);

     UserTakeActivityCount queryUserTakeActivityCount(UserTakeActivityCount req);
}

