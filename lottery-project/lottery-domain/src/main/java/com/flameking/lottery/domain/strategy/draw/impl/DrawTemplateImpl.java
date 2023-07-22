package com.flameking.lottery.domain.strategy.draw.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.flameking.lottery.domain.strategy.algorithm.ILotteryStrategy;
import com.flameking.lottery.domain.strategy.factory.LotteryStrategyFactory;
import com.flameking.lottery.infrastructure.entity.Award;
import com.flameking.lottery.infrastructure.entity.Strategy;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import com.flameking.lottery.infrastructure.service.IAwardService;
import com.flameking.lottery.domain.strategy.draw.IDrawTemplate;
import com.flameking.lottery.infrastructure.service.IStrategyDetailService;
import com.flameking.lottery.infrastructure.service.IStrategyService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class DrawTemplateImpl implements IDrawTemplate {
    @Resource
    private IStrategyDetailService strategyDetailService;
    @Resource
    private IStrategyService strategyService;
    @Resource
    private IAwardService awardService;

    /**
     * 抽奖策略是否初始化
     *
     * @param strategy
     * @param awardInfos
     */
    private ILotteryStrategy checkAndInitStrategy(Strategy strategy, List<StrategyDetail> awardInfos){
        Integer strategyMode = strategy.getStrategyMode();
        ILotteryStrategy lotteryStrategy = LotteryStrategyFactory.getLotteryStrategy(strategy.getStrategyMode());
        lotteryStrategy.checkAndInitStrategy(strategy.getStrategyId(), awardInfos);
        if (strategyMode == 1){
            lotteryStrategy.init(strategy.getStrategyId());
        }
        return lotteryStrategy;
    }

    @Override
    public Award doDraw(Long uId, Long strategyId) {
        //查询奖品信息和被排除的奖品信息
        List<StrategyDetail> awardInfos = strategyDetailService.getStrategyDetailsByStrategyId(strategyId);

        //根据 strategyId 查询策略信息
        Strategy strategy = strategyService.getByStrategyId(strategyId);

        //初始化抽奖策略
        ILotteryStrategy lotteryStrategy = checkAndInitStrategy(strategy, awardInfos);

        //排除掉的奖品id
        List<Long> excludeAwardIds = strategyDetailService.getExcludedAwardIds(strategyId);

        //执行抽奖
        String awardId = lotteryStrategy.draw(strategyId, excludeAwardIds);

        //未中奖
        if (awardId == null){
            return null;
        }

        //抽奖成功，扣减库存
        boolean isSuccess = strategyDetailService.decreaseLeftAwardCount(strategyId, awardId);

        //包装奖品信息
        return isSuccess ? awardService.getAwardByAwardId(Long.valueOf(awardId)) : null;
    }
}
