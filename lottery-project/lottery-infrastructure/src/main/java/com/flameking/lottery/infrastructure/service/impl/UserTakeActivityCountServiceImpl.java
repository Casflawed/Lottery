package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.UserTakeActivityCount;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.UserTakeActivityCountMapper;
import com.flameking.lottery.infrastructure.service.IUserTakeActivityCountService;
import org.springframework.stereotype.Service;


/**
 * 用户活动参与次数服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-08-15 21:20:14
 */
@Service
public class UserTakeActivityCountServiceImpl extends ServiceImpl<UserTakeActivityCountMapper, UserTakeActivityCount> implements IUserTakeActivityCountService {

    @Resource
    private UserTakeActivityCountMapper userTakeActivityCountMapper;


//    @Override
//    public IPage<UserTakeActivityCount> getPage(UserTakeActivityCountQuery query){
//        Page<UserTakeActivityCount> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<UserTakeActivityCount> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }

    @Override
    public UserTakeActivityCount findById(String id) {
        return getById(id);
    }

    @Override
    public Long create(UserTakeActivityCount userTakeActivityCount) {
        save(userTakeActivityCount);
        return userTakeActivityCount.getId();
    }

    @Override
    public boolean update(UserTakeActivityCount userTakeActivityCount) {
        return updateById(userTakeActivityCount);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

    @Override
    public UserTakeActivityCount queryUserTakeActivityCount(UserTakeActivityCount req) {
        return getOne(new LambdaQueryWrapper<UserTakeActivityCount>()
                .eq(UserTakeActivityCount::getUId, req.getUId())
                .eq(UserTakeActivityCount::getActivityId, req.getActivityId()));
    }

}

