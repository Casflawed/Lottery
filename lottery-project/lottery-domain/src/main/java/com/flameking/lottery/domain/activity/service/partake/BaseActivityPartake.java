package com.flameking.lottery.domain.activity.service.partake;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;

/**
 * 活动领取模板抽象类
 */
public abstract class BaseActivityPartake implements IActivityPartake {

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
        Result subtractionActivityResult = subtractionActivityStock(req);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(subtractionActivityResult.getCode())) {
            return new PartakeResult(subtractionActivityResult.getCode(), subtractionActivityResult.getInfo());
        }
        //更新or插入用户参与次数信息
        Result grabResult = grabActivity(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            return new PartakeResult(grabResult.getCode(), grabResult.getInfo());
        }

        //查询用户参与活动信息
        PartakeResult partakeResult = new PartakeResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        partakeResult.setStrategyId(activityBillVO.getStrategyId());
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
    protected abstract Result grabActivity(PartakeReq partake, ActivityBillVO bill);

}
