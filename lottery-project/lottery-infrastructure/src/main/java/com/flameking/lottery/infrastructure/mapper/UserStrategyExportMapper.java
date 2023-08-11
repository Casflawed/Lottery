package com.flameking.lottery.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flameking.lottery.infrastructure.entity.UserStrategyExport;
import com.flameking.middleware.db.router.annotation.DBRouterStrategy;


/**
 * 用户策略计算结果 mapper 类
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
@DBRouterStrategy(splitTable = true)
public interface UserStrategyExportMapper extends BaseMapper<UserStrategyExport> {


}

