package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.UserStrategyExport;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.UserStrategyExportMapper;
import com.flameking.lottery.infrastructure.service.IUserStrategyExportService;
import com.flameking.middleware.db.router.annotation.DbRouter;
import org.springframework.stereotype.Service;


/**
 * 用户策略计算结果服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
@Service
public class UserStrategyExportServiceImpl extends ServiceImpl<UserStrategyExportMapper, UserStrategyExport> implements IUserStrategyExportService {

    @Resource
    private UserStrategyExportMapper userStrategyExportMapper;


//    @Override
//    public IPage<UserStrategyExport> getPage(UserStrategyExport001Query query){
//        Page<UserStrategyExport> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<UserStrategyExport> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }

    @Override
    @DbRouter
    public UserStrategyExport findById(Long id) {
        return getById(id);
    }

    @Override
    @DbRouter
    public Long create(UserStrategyExport userStrategyExport) {
        save(userStrategyExport);
        return userStrategyExport.getId();
    }

    @Override
    public boolean update(UserStrategyExport userStrategyExport) {
        return updateById(userStrategyExport);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

}

