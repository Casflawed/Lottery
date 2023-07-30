package com.flameking.lottery.domain.activity.repository;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.model.vo.ActivityVO;
import com.flameking.lottery.domain.activity.model.vo.AwardVO;
import com.flameking.lottery.domain.activity.model.vo.StrategyDetailVO;
import com.flameking.lottery.domain.activity.model.vo.StrategyVO;

import java.util.List;

public interface IActivityRepository {
    boolean insertActivity(ActivityVO activityVO);

    boolean insertAwards(List<AwardVO> awardVOS);

    boolean insertStrategy(StrategyVO strategyVO);

    boolean insertStrategyDetails(List<StrategyDetailVO> strategyDetailVOS);

    /**
     * 变更活动状态
     *
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @param transferState 转换后状态
     */
    boolean alterStatus(Long activityId, Constants.ActivityState currentState, Constants.ActivityState transferState);

}