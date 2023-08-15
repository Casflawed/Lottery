package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.util.EntityUtils;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.vo.*;
import com.flameking.lottery.domain.activity.repository.IActivityRepository;
import com.flameking.lottery.infrastructure.entity.*;
import com.flameking.lottery.infrastructure.service.*;
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
    @Autowired
    private IUserTakeActivityCountService userTakeActivityCountService;

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

    @Override
    public ActivityBillVO queryActivityBill(PartakeReq req) {

        // 查询活动信息
        Activity activity = activityService.queryActivityById(req.getActivityId());

        // 查询可领取次数和剩余领取次数
        UserTakeActivityCount userTakeActivityCountReq = new UserTakeActivityCount();
        EntityUtils.copyProperties(req, userTakeActivityCountReq);

        UserTakeActivityCount userTakeActivityCount = userTakeActivityCountService.queryUserTakeActivityCount(userTakeActivityCountReq);

        // 封装结果信息
        ActivityBillVO activityBillVO = new ActivityBillVO();
        activityBillVO.setUId(req.getUId());
        activityBillVO.setUserTakeLeftCount(null == userTakeActivityCount ? null : userTakeActivityCount.getLeftCount());
        EntityUtils.copyProperties(activity, activityBillVO);

        return activityBillVO;
    }

    @Override
    public boolean subtractionActivityStock(Long activityId) {
        return activityService.subtractionActivityStock(activityId);
    }
}
