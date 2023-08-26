package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.UserStrategyExport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户策略计算结果服务层接口
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
public interface IUserStrategyExportService extends IService<UserStrategyExport> {

//     IPage<UserStrategyExport> getPage(UserStrategyExport001Query query);


     UserStrategyExport findById(Long id);

     Long create(UserStrategyExport userStrategyExport);

     boolean update(UserStrategyExport userStrategyExport);

     boolean del(String id);

}

