package com.flameking.lottery.domain.activity.service.deploy.impl;

import com.alibaba.fastjson.JSON;
import com.flameking.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import com.flameking.lottery.domain.activity.model.req.ActivityConfigReq;
import com.flameking.lottery.domain.activity.model.vo.ActivityVO;
import com.flameking.lottery.domain.activity.model.vo.AwardVO;
import com.flameking.lottery.domain.activity.model.vo.StrategyVO;
import com.flameking.lottery.domain.activity.repository.IActivityRepository;
import com.flameking.lottery.domain.activity.service.deploy.IActivityDeploy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class IActivityDeployImpl implements IActivityDeploy {
    @Autowired
    private IActivityRepository activityRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createActivity(ActivityConfigReq req) {
        ActivityConfigRich activityConfigRich = req.getActivityConfigRich();
        try {
            //创建活动
            ActivityVO activity = activityConfigRich.getActivity();
            activityRepository.insertActivity(activity);

            //创建奖品信息
            List<AwardVO> awardList = activityConfigRich.getAwardList();
            activityRepository.insertAwards(awardList);

            //创建策略
            StrategyVO strategy = activityConfigRich.getStrategy();
            activityRepository.insertStrategy(strategy);

            //创建概率奖品信息
            activityRepository.insertStrategyDetails(strategy.getStrategyDetailList());

        } catch (DuplicateKeyException e) {
            log.error("创建活动配置失败，唯一索引冲突 activityId：{} reqJson：{}", req.getActivityId(), JSON.toJSONString(req), e);
            throw e;
        }
    }

    @Override
    public List<ActivityVO> scanToDoActivityList(Long id) {
        return activityRepository.scanToDoActivityList(id);
    }
}
