package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.util.EntityUtils;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.StockResult;
import com.flameking.lottery.domain.activity.model.vo.*;
import com.flameking.lottery.domain.activity.repository.IActivityRepository;
import com.flameking.lottery.infrastructure.entity.*;
import com.flameking.lottery.infrastructure.service.*;
import com.flameking.lottery.infrastructure.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityRepositoryImpl implements IActivityRepository {
    private Logger logger = LoggerFactory.getLogger(ActivityRepositoryImpl.class);

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
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean insertActivity(ActivityVO activityVO) {
        Activity activity = new Activity();
        EntityUtils.copyProperties(activityVO, activity);
        boolean save = activityService.save(activity);
        redisUtil.set(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activity.getActivityId()), 0);
        return save;
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
    public void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        // 删除分布式锁 Key
        redisUtil.del(tokenKey);
    }

    @Override
    public StockResult subtractionActivityStockByRedis(String uId, Long activityId, Integer stockCount) {

        //  1. 获取抽奖活动库存 Key
        String stockKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityId);

        // 2. 扣减库存，目前占用库存数
        // TODO: 2023/9/6 redisUtil.incr(stockKey, 1) 返回值是什么
        Integer stockUsedCount = (int) redisUtil.incr(stockKey, 1);

        // 3. 超出库存判断，进行恢复原始库存
        if (stockUsedCount > stockCount) {
            redisUtil.decr(stockKey, 1);
            return new StockResult(Constants.ResponseCode.OUT_OF_STOCK.getCode(), Constants.ResponseCode.OUT_OF_STOCK.getInfo());
        }

        // 4. 以活动库存占用编号，生成对应加锁Key，细化锁的颗粒度
        String stockTokenKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN(activityId, stockUsedCount);

        // 5. 使用 Redis.setNx 加一个分布式锁
        boolean lockToken = redisUtil.setNx(stockTokenKey, 350L);
        if (!lockToken) {
            logger.info("抽奖活动{}用户秒杀{}扣减库存，分布式锁失败：{}", activityId, uId, stockTokenKey);
            return new StockResult(Constants.ResponseCode.ERR_TOKEN.getCode(), Constants.ResponseCode.ERR_TOKEN.getInfo());
        }

        return new StockResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), stockTokenKey, stockCount - stockUsedCount);
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

    @Override
    public List<ActivityVO> scanToDoActivityList(Long id) {
        List<Activity> activityList = activityService.scanToDoActivityList(id);
        return EntityUtils.toList(activityList, ActivityVO::new);
    }
}
