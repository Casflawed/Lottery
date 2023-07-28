package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.common.util.EntityUtils;
import com.flameking.lottery.domain.strategy.model.vo.StrategyRich;
import com.flameking.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.flameking.lottery.domain.strategy.model.vo.StrategyBriefVO;
import com.flameking.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import com.flameking.lottery.domain.strategy.repository.IStrategyRepository;
import com.flameking.lottery.infrastructure.entity.Award;
import com.flameking.lottery.infrastructure.entity.Strategy;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import com.flameking.lottery.infrastructure.service.IAwardService;
import com.flameking.lottery.infrastructure.service.IStrategyDetailService;
import com.flameking.lottery.infrastructure.service.IStrategyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StrategyRepositoryImpl implements IStrategyRepository {
    @Autowired
    private IStrategyDetailService strategyDetailService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IAwardService awardService;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        List<StrategyDetail> strategyDetails = strategyDetailService.getStrategyDetailsByStrategyId(strategyId);
        List<StrategyDetailBriefVO> strategyDetailBriefVOS = EntityUtils.toList(strategyDetails, StrategyDetailBriefVO::new);

        Strategy strategy = strategyService.getByStrategyId(strategyId);
        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        EntityUtils.copyProperties(strategy, strategyBriefVO);

        return new StrategyRich(strategyId, strategyBriefVO, strategyDetailBriefVOS);
    }

    @Override
    public AwardBriefVO queryAward(Long awardId) {
        Award awardInfo = awardService.getAwardByAwardId(awardId);
        AwardBriefVO awardBriefVO = new AwardBriefVO();
        BeanUtils.copyProperties(awardInfo, awardBriefVO);

        return awardBriefVO;
    }

    @Override
    public List<Long> queryAwardIdsWithoutAmount(Long strategyId) {
        return strategyDetailService.queryAwardIdsWithoutAmount(strategyId);
    }

    @Override
    public boolean decreaseLeftAwardCount(Long strategyId, Long awardId) {
        return strategyDetailService.decreaseLeftAwardCount(strategyId, awardId);
    }
}
