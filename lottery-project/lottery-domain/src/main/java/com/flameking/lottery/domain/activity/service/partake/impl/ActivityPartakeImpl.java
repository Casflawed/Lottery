package com.flameking.lottery.domain.activity.service.partake.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;
import com.flameking.lottery.domain.activity.service.partake.IActivityPartake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityPartakeImpl implements IActivityPartake {
    @Autowired

    @Override
    public PartakeResult doPartake(PartakeReq req) {
        //查询活动信息和用户参与活动次数信息
        ActivityBillVO activityBillVO = queryActivityBill(req);

        //校验活动，包括活动状态、活动进行时间、活动可参与次数和用户个人剩余参与次数
        Result checkResult = checkActivityBill(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())) {
            return new PartakeResult(checkResult.getCode(), checkResult.getInfo());
        }
        //扣减活动剩余参与次数

        //更新or插入用户参与次数信息

        //查询用户参与活动信息

        return null;
    }

    private Result checkActivityBill(PartakeReq req, ActivityBillVO activityBillVO) {
        return null;
    }

    private ActivityBillVO queryActivityBill(PartakeReq req) {
        return null;
    }

}
