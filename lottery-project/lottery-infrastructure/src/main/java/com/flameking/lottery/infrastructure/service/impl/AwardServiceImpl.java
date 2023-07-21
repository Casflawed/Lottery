package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.Award;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.AwardMapper;
import com.flameking.lottery.infrastructure.service.IAwardService;
import org.springframework.stereotype.Service;


/**
 * 奖品配置服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-07-21 17:00:12
 */
@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements IAwardService {

    @Resource
    private AwardMapper awardMapper;

    @Override
    public Award findById(String id) {
        return getById(id);
    }

    @Override
    public Long create(Award award) {
        save(award);
        return award.getId();
    }

    @Override
    public boolean update(Award award) {
        return updateById(award);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

    @Override
    public Award getAwardByAwardId(Long awardId) {
        return getOne(new LambdaQueryWrapper<Award>().eq(Award::getAwardId, awardId));
    }

}

