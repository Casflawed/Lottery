package com.flameking.lottery.domain.award.goods.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.award.goods.AwardSenderBase;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.model.req.GoodsReq;
import com.flameking.lottery.domain.award.model.res.AwardSenderRes;
import org.springframework.stereotype.Component;

/**
 * 优惠券商品
 */
@Component
public class CouponGoods extends AwardSenderBase implements ISendGoods {

    @Override
    public AwardSenderRes doSend(GoodsReq req) {

        // 模拟调用优惠券发放接口
        logger.info("模拟调用优惠券发放接口 uId：{} awardContent：{}", req.getUId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getUId(), req.getOrderId(), req.getAwardId(), Constants.GrantState.COMPLETE.getCode());

        return new AwardSenderRes(req.getUId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getSeedGoodsName() {
        return Constants.AwardType.CouponGoods.getCode();
    }

}