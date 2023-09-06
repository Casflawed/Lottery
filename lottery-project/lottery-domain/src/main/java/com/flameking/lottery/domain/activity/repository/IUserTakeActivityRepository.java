package com.flameking.lottery.domain.activity.repository;

import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.vo.*;

import java.util.List;

public interface IUserTakeActivityRepository {
    boolean takeActivity(PartakeReq partake, ActivityBillVO bill, Long takeId);

    boolean lockTackActivity(String uId, Long activityId, Long takeId);

    void saveUserStrategyExport(DrawOrderVO drawOrder);

    /**
     * 更新发货单MQ状态
     *
     * @param uId     用户ID
     * @param orderId 订单ID
     * @param mqState MQ 发送状态
     */
    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);

    /**
     * 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
     *
     * @param activityId 活动ID
     * @param uId        用户ID
     * @return 领取单
     */
    UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);

    /**
     * 扫描发货单 MQ 状态，把超时30分钟未发送 MQ 和发送 MQ 失败的单子扫描出来，做补偿
     *
     * @return 发货单
     */
    List<InvoiceVO> scanInvoiceMqState();
    /**
     * 更新活动库存
     *
     * @param activityPartakeRecordVO   活动领取记录
     */
    boolean updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO);
}
