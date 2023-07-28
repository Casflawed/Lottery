package com.flameking.lottery.infrastructure.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flameking.lottery.infrastructure.entity.Activity;
import com.flameking.lottery.infrastructure.mapper.ActivityMapper;
import com.flameking.lottery.infrastructure.service.IActivityService;
import org.springframework.stereotype.Service;

/**
 * 活动配置服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-07-21 15:12:54
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

}
