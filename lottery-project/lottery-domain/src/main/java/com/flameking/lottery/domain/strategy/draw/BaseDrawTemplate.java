package com.flameking.lottery.domain.strategy.draw;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.util.EntityUtils;
import com.flameking.lottery.domain.strategy.algorithm.IRandomDrawAlgorithm;
import com.flameking.lottery.domain.strategy.factory.RandomDrawAlgorithmFactory;
import com.flameking.lottery.domain.strategy.model.req.DrawReq;
import com.flameking.lottery.domain.strategy.model.vo.StrategyRich;
import com.flameking.lottery.domain.strategy.model.res.DrawResult;
import com.flameking.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.flameking.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.flameking.lottery.domain.strategy.model.vo.StrategyBriefVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Slf4j
public abstract class BaseDrawTemplate extends DrawStrategySupport implements IDrawTemplate{


    @Override
    public DrawResult doDraw(DrawReq req) {
        Long strategyId = req.getStrategyId();
        String uId = req.getUId();
        //查询奖品概率和策略配置信息
        StrategyRich strategyRich = this.queryStrategyRich(strategyId);
        StrategyBriefVO strategy = strategyRich.getStrategy();

        //初始化抽奖策略
        this.initRandomDrawAlgorithm(strategy.getStrategyMode(), strategyRich);

        //查询不在抽奖范围内的奖品
        List<Long> excludeAwardIds = this.queryExcludeAwardIds(strategyId);

        //执行抽奖
        Long awardId = this.excRandomDrawAlgorithm(strategyId, strategy.getStrategyMode(), excludeAwardIds);

        //包装奖品信息
        return this.wrappedLotteryResult(uId, strategyId, awardId, strategy);
    }

    /**
     * 初始化抽奖算法的配置
     *
     * @param strategyMode
     * @param strategyRich
     */
    private void initRandomDrawAlgorithm(Integer strategyMode, StrategyRich strategyRich) {
        IRandomDrawAlgorithm randomDrawAlgorithm = RandomDrawAlgorithmFactory.getRandomDrawAlgorithm(strategyMode);
        randomDrawAlgorithm.initAwardRateInfo(strategyRich.getStrategyId(), strategyRich.getStrategyDetailList());
    }


    private DrawResult wrappedLotteryResult(String uId, Long strategyId, Long awardId, StrategyBriefVO strategy){
        if (awardId == null){
            log.debug("用户-{}-抽奖策略-{}-抽奖状态-{}", uId, strategyId, Constants.DrawState.FAIL.getInfo());
            return new DrawResult(uId, strategyId, Constants.DrawState.FAIL.getCode(), Constants.DrawState.FAIL.getInfo());
        }
        log.debug("用户-{}-抽奖策略-{}-抽奖状态-{}", uId, strategyId, Constants.DrawState.FAIL.getInfo());

        AwardBriefVO awardBriefVO = this.queryAward(awardId);
        DrawAwardInfo drawAwardInfo = new DrawAwardInfo();
        EntityUtils.copyProperties(awardBriefVO, drawAwardInfo);
        EntityUtils.copyProperties(strategy, drawAwardInfo);

        return new DrawResult(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), Constants.DrawState.SUCCESS.getInfo(), drawAwardInfo);
    }

    /**
     * 执行抽奖算法
     *
     * @param strategyId
     * @param strategyMode
     * @param excludeAwardIds
     */
    protected abstract Long excRandomDrawAlgorithm(Long strategyId, Integer strategyMode, List<Long> excludeAwardIds);

    /**
     * 查询查询不在抽奖范围内的奖品
     *
     * @param strategyId
     */
    protected abstract List<Long> queryExcludeAwardIds(Long strategyId);
}
