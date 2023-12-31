package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.infrastructure.entity.UserStrategyExport;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.UserStrategyExportMapper;
import com.flameking.lottery.infrastructure.service.IUserStrategyExportService;
import com.flameking.middleware.db.router.annotation.DBRouter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


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
    @DBRouter
    public UserStrategyExport findById(Long id) {
        return getById(id);
    }

    @Override
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

    @Override
    public boolean updateInvoiceMqState(String uId, Long orderId, Integer mqState) {

        return update(new LambdaUpdateWrapper<UserStrategyExport>()
                .eq(UserStrategyExport::getUId, uId)
                .eq(UserStrategyExport::getOrderId, orderId)
                .set(UserStrategyExport::getMqState, mqState));
    }

    @Override
    public boolean updateUserAwardState(String uId, Long orderId, Long awardId, Integer grantState) {
        return update(new LambdaUpdateWrapper<UserStrategyExport>()
                .eq(UserStrategyExport::getUId, uId)
                .eq(UserStrategyExport::getOrderId, orderId)
                .eq(UserStrategyExport::getAwardId, awardId)
                .set(UserStrategyExport::getGrantState, grantState)
                .set(UserStrategyExport::getGrantDate, new Date())
                .set(UserStrategyExport::getUpdateTime, new Date()));
    }

    @Override
    public List<UserStrategyExport> scanInvoiceMqState() {
        return list(new LambdaQueryWrapper<UserStrategyExport>()
                .eq(UserStrategyExport::getMqState, Constants.MQState.FAIL.getCode())
                .or(i -> i.eq(UserStrategyExport::getMqState, 0)
                        .apply("now() - create_time > 1800000")));
    }

}

