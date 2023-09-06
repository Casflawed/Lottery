package com.flameking.lottery.domain.activity.repository;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.StockResult;
import com.flameking.lottery.domain.activity.model.vo.*;

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

    /**
     * 查询活动账单信息【库存、状态、日期、个人参与次数】
     * @param req 参与活动请求
     * @return    活动账单
     */
    ActivityBillVO queryActivityBill(PartakeReq req);

    /**
     * 扣减活动库存
     *
     * @param activityId 活动ID
     * @return 扣减结果
     */
    boolean subtractionActivityStock(Long activityId);

    /**
     * 扫描待处理的活动列表，状态为：通过、运行中
     *
     * @param id ID
     * @return 待处理的活动集合
     */
    List<ActivityVO> scanToDoActivityList(Long id);
    /**
     * 扣减活动库存，通过Redis
     *
     * @param uId        用户ID
     * @param activityId 活动ID
     * @param stockCount 总库存
     * @return 扣减结果
     */
    StockResult subtractionActivityStockByRedis(String uId, Long activityId, Integer stockCount);

    /**
     * 恢复活动库存，通过Redis 【如果非常异常，则需要进行缓存库存恢复，只保证不超卖的特性，所以不保证一定能恢复占用库存，另外最终可以由任务进行补偿库存】
     *
     * @param activityId    活动ID
     * @param tokenKey      分布式 KEY 用于清理
     * @param code          状态
     */
    void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code);
}