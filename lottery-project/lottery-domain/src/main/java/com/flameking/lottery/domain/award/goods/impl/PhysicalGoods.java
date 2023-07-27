package com.flameking.lottery.domain.award.goods.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.award.goods.AwardSenderBase;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.model.req.GoodsReq;
import com.flameking.lottery.domain.award.model.res.AwardSenderRes;
import org.springframework.stereotype.Component;

/**
 * 实物发货类商品
 */
@Component
public class PhysicalGoods extends AwardSenderBase implements ISendGoods {

    @Override
    public AwardSenderRes doSend(GoodsReq req) {

        // 模拟调用实物发奖
        logger.info("模拟调用实物发奖 uId：{} awardContent：{}", req.getUId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getUId(), req.getOrderId(), req.getAwardId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());

        return new AwardSenderRes(req.getUId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getSeedGoodsName() {
        return Constants.AwardType.PhysicalGoods.getCode();
    }

}
