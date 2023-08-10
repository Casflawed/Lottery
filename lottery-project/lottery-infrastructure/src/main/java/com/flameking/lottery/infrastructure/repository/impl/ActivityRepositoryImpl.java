package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.util.EntityUtils;
import com.flameking.lottery.domain.activity.model.vo.ActivityVO;
import com.flameking.lottery.domain.activity.model.vo.AwardVO;
import com.flameking.lottery.domain.activity.model.vo.StrategyDetailVO;
import com.flameking.lottery.domain.activity.model.vo.StrategyVO;
import com.flameking.lottery.domain.activity.repository.IActivityRepository;
import com.flameking.lottery.infrastructure.entity.Activity;
import com.flameking.lottery.infrastructure.entity.Award;
import com.flameking.lottery.infrastructure.entity.Strategy;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import com.flameking.lottery.infrastructure.service.IActivityService;
import com.flameking.lottery.infrastructure.service.IAwardService;
import com.flameking.lottery.infrastructure.service.IStrategyDetailService;
import com.flameking.lottery.infrastructure.service.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityRepositoryImpl implements IActivityRepository {
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IAwardService awardService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyDetailService strategyDetailService;

    @Override
    public boolean insertActivity(ActivityVO activityVO) {
        Activity activity = new Activity();
        EntityUtils.copyProperties(activityVO, activity);

        return activityService.save(activity);
    }

    @Override
    public boolean insertAwards(List<AwardVO> awardVOS) {
        List<Award> awards = EntityUtils.toList(awardVOS, Award::new);

        return awardService.saveBatch(awards);
    }

    @Override
    public boolean insertStrategy(StrategyVO strategyVO) {
        Strategy strategy = new Strategy();
        EntityUtils.copyProperties(strategyVO, strategy);

        return strategyService.save(strategy);
    }

    @Override
    public boolean insertStrategyDetails(List<StrategyDetailVO> strategyDetailVOS) {
        List<StrategyDetail> strategyDetails = EntityUtils.toList(strategyDetailVOS, StrategyDetail::new);

        return strategyDetailService.saveBatch(strategyDetails);

    }

    @Override
    public boolean alterStatus(Long activityId, Constants.ActivityState currentState, Constants.ActivityState transferState) {
        return activityService.alterState(activityId, currentState, transferState);
    }
}
