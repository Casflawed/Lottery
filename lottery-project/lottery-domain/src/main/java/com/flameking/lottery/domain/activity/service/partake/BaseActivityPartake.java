package com.flameking.lottery.domain.activity.service.partake;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.model.res.StockResult;
import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;
import com.flameking.lottery.domain.activity.model.vo.UserTakeActivityVO;
import com.flameking.lottery.domain.ids.IIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 活动领取模板抽象类
 */
public abstract class BaseActivityPartake implements IActivityPartake {
    @Autowired
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public PartakeResult doPartake(PartakeReq req) {
        // 1. 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
        UserTakeActivityVO userTakeActivityVO = this.queryNoConsumedTakeActivityOrder(req.getActivityId(), req.getUId());
        if (null != userTakeActivityVO) {
            return buildPartakeResult(userTakeActivityVO.getStrategyId(), userTakeActivityVO.getTakeId());
        }

        //查询活动信息和用户参与活动次数信息
        ActivityBillVO activityBillVO = queryActivityBill(req);

        //校验活动，包括活动状态、活动进行时间、活动可参与次数和用户个人剩余参与次数
        Result checkResult = checkActivityBill(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())) {
            return new PartakeResult(checkResult.getCode(), checkResult.getInfo());
        }
        //扣减活动剩余参与次数
        StockResult subtractionActivityResult = this.subtractionActivityStock(req.getUId(), req.getActivityId(), activityBillVO.getStockCount());

//        Result subtractionActivityResult = subtractionActivityStock(req);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(subtractionActivityResult.getCode())) {
            this.recoverActivityCacheStockByRedis(req.getActivityId(), subtractionActivityResult.getStockKey(), subtractionActivityResult.getCode());
            return new PartakeResult(subtractionActivityResult.getCode(), subtractionActivityResult.getInfo());
        }

        //更新or插入用户参与次数信息
        Long takeId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
        Result grabResult = grabActivity(req, activityBillVO, takeId);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            this.recoverActivityCacheStockByRedis(req.getActivityId(), subtractionActivityResult.getStockKey(), grabResult.getCode());
            return new PartakeResult(grabResult.getCode(), grabResult.getInfo());
        }

        // 6. 扣减活动库存，通过Redis End
        this.recoverActivityCacheStockByRedis(req.getActivityId(), subtractionActivityResult.getStockKey(), Constants.ResponseCode.SUCCESS.getCode());

        //查询用户参与活动信息
        return new PartakeResult(Constants.ResponseCode.SUCCESS.getCode(),
                Constants.ResponseCode.SUCCESS.getInfo(),
                activityBillVO.getStrategyId(), takeId);
    }
    /**
     * 恢复活动库存，通过Redis 【如果非常异常，则需要进行缓存库存恢复，只保证不超卖的特性，所以不保证一定能恢复占用库存，另外最终可以由任务进行补偿库存】
     *
     * @param activityId 活动ID
     * @param tokenKey   分布式 KEY 用于清理
     * @param code       状态
     */
    protected abstract void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code);

    /**
     * 扣减活动库存，通过Redis
     *
     * @param uId        用户ID
     * @param activityId 活动号
     * @param stockCount 总库存
     * @return 扣减结果
     */
    protected abstract StockResult subtractionActivityStock(String uId, Long activityId, Integer stockCount);

    /**
     * 封装结果【返回的策略ID，用于继续完成抽奖步骤】
     *
     * @param strategyId 策略ID
     * @param takeId     领取ID
     * @return 封装结果
     */
    private PartakeResult buildPartakeResult(Long strategyId, Long takeId) {
        PartakeResult partakeResult = new PartakeResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        partakeResult.setStrategyId(strategyId);
        partakeResult.setTakeId(takeId);
        return partakeResult;
    }

    /**
     * 扣减活动可参与次数
     */
    protected abstract Result subtractionActivityStock(PartakeReq req);


    /**
     * 检查活动信息和用户剩余参与次数信息
     */
    protected abstract Result checkActivityBill(PartakeReq partake, ActivityBillVO bill);

    /**
     * 查询活动信息和用于剩余参与次数信息
     */
    protected abstract ActivityBillVO queryActivityBill(PartakeReq req);

    /**
     * 参与活动，扣减用于剩余参与次数，插入用户参与活动记录
     */
    protected abstract Result grabActivity(PartakeReq partake, ActivityBillVO bill, Long takeId);

    /**
     * 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
     *
     * @param activityId 活动ID
     * @param uId        用户ID
     * @return 领取单
     */
    protected abstract UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);

}
