package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.UserTakeActivity;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.UserTakeActivityMapper;
import com.flameking.lottery.infrastructure.service.IUserTakeActivityService;
import com.flameking.middleware.db.router.annotation.DBRouter;
import org.springframework.stereotype.Service;


/**
 * 用户参与活动记录服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
@Service
public class UserTakeActivityServiceImpl extends ServiceImpl<UserTakeActivityMapper, UserTakeActivity> implements IUserTakeActivityService {

    @Resource
    private UserTakeActivityMapper userTakeActivityMapper;


//    @Override
//    public IPage<UserTakeActivity> getPage(UserTakeActivityQuery query){
//        Page<UserTakeActivity> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<UserTakeActivity> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }

    @Override
    @DBRouter
    public UserTakeActivity findById(Long id) {
        return getById(id);
    }

    @Override
    public boolean create(UserTakeActivity userTakeActivity) {
        return save(userTakeActivity);
    }

    @Override
    public boolean update(UserTakeActivity userTakeActivity) {
        LambdaUpdateWrapper<UserTakeActivity> w = new LambdaUpdateWrapper<UserTakeActivity>().setSql("state = 1")
                .eq(UserTakeActivity::getUId, userTakeActivity.getUId())
                .eq(UserTakeActivity::getActivityId, userTakeActivity.getActivityId())
                .eq(UserTakeActivity::getState, 0);
        return update(w);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

}

