package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.Award;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 奖品配置服务层接口
 *
 * @author WangWei
 * @dateTime 2023-07-21 17:00:12
 */
public interface IAwardService extends IService<Award> {

     Award findById(String id);

     Long create(Award award);

     boolean update(Award award);

     boolean del(String id);

     Award getAwardByAwardId(Long awardId);
}

