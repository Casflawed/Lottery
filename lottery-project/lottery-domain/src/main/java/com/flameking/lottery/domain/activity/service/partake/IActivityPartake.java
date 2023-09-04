package com.flameking.lottery.domain.activity.service.partake;

import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.model.vo.DrawOrderVO;
import com.flameking.lottery.domain.activity.model.vo.UserTakeActivityVO;

public interface IActivityPartake {
    /**
     * 参与活动
     */
    PartakeResult doPartake(PartakeReq req);

    /**
     * 抽奖结果落库
     */
    Result recordDrawOrder(DrawOrderVO drawOrder);

    /**
     * 更新发货单MQ状态
     *
     * @param uId     用户ID
     * @param orderId 订单ID
     * @param mqState MQ 发送状态
     */
    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);
}
