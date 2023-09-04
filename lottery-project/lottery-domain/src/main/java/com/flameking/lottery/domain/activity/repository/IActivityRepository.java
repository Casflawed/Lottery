package com.flameking.lottery.domain.activity.repository;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
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
}