package com.flameking.lottery.domain.award.goods.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.award.goods.AwardSenderBase;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.model.req.GoodsReq;
import com.flameking.lottery.domain.award.model.res.AwardSenderRes;
import org.springframework.stereotype.Component;

/**
 * 兑换码类商品
 */
@Component
public class RedeemCodeGoods extends AwardSenderBase implements ISendGoods {

    @Override
    public AwardSenderRes doSend(GoodsReq req) {

        // 模拟调用兑换码
        logger.info("模拟调用兑换码 uId：{} awardContent：{}", req.getUId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getUId(), req.getOrderId(), req.getAwardId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());

        return new AwardSenderRes(req.getUId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getSeedGoodsName() {
        return Constants.AwardType.RedeemCodeGoods.getCode();
    }

}
