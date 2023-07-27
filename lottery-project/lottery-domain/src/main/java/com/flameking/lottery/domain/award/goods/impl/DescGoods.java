package com.flameking.lottery.domain.award.goods.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.award.goods.AwardSenderBase;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.model.req.GoodsReq;
import com.flameking.lottery.domain.award.model.res.AwardSenderRes;
import org.springframework.stereotype.Component;

/**
 * 描述类商品，以文字形式展示给用户
 */
@Component
public class DescGoods extends AwardSenderBase implements ISendGoods {

    @Override
    public AwardSenderRes doSend(GoodsReq req) {

        super.updateUserAwardState(req.getUId(), req.getOrderId(), req.getAwardId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());

        return new AwardSenderRes(req.getUId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getSeedGoodsName() {
        return Constants.AwardType.DESC.getCode();
    }

}
