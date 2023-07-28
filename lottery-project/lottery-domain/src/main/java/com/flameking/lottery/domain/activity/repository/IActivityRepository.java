package com.flameking.lottery.domain.activity.repository;

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
}
