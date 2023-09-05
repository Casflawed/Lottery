package com.flameking.lottery.domain.activity.service.partake;

import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.model.vo.DrawOrderVO;
import com.flameking.lottery.domain.activity.model.vo.InvoiceVO;
import com.flameking.lottery.domain.activity.model.vo.UserTakeActivityVO;

import java.util.List;

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

    /**
     * 扫描发货单 MQ 状态，把超时30分钟未发送 MQ 和发送 MQ 失败的单子扫描出来，做补偿
     *
     * @param dbCount 指定分库
     * @param tbCount 指定分表
     * @return 发货单
     */
    List<InvoiceVO> scanInvoiceMqState(int dbCount, int tbCount);
}
