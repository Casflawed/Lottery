package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.UserTakeActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flameking.middleware.db.router.annotation.DbRouter;

/**
 * 用户参与活动记录服务层接口
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
public interface IUserTakeActivityService extends IService<UserTakeActivity> {

//     IPage<UserTakeActivity> getPage(UserTakeActivityQuery query);

     UserTakeActivity findById(Long id);

     Long create(UserTakeActivity userTakeActivity);

     boolean update(UserTakeActivity userTakeActivity);

     boolean del(String id);

}

