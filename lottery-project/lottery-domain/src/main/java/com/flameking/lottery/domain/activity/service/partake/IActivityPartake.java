package com.flameking.lottery.domain.activity.service.partake;

import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.model.vo.DrawOrderVO;

public interface IActivityPartake {
    /**
     * 参与活动
     */
    PartakeResult doPartake(PartakeReq req);

    /**
     * 抽奖结果落库
     */
    Result recordDrawOrder(DrawOrderVO drawOrder);
}
