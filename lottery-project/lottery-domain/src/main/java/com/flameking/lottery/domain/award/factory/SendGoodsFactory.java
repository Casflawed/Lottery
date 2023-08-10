package com.flameking.lottery.domain.award.factory;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.goods.impl.CouponGoods;
import com.flameking.lottery.domain.award.goods.impl.DescGoods;
import com.flameking.lottery.domain.award.goods.impl.PhysicalGoods;
import com.flameking.lottery.domain.award.goods.impl.RedeemCodeGoods;

import java.util.HashMap;
import java.util.Map;

/**
 * 分发商品静态工厂类
 */
public class SendGoodsFactory {
    public final static Map<Integer, ISendGoods> sendGoodsMap = new HashMap<>();

    static {
        sendGoodsMap.put(Constants.AwardType.DESC.getCode(), new DescGoods());
        sendGoodsMap.put(Constants.AwardType.RedeemCodeGoods.getCode(), new RedeemCodeGoods());
        sendGoodsMap.put(Constants.AwardType.CouponGoods.getCode(), new CouponGoods());
        sendGoodsMap.put(Constants.AwardType.PhysicalGoods.getCode(), new PhysicalGoods());

    }

    public static ISendGoods getSendGoods(int strategyMode){
        return sendGoodsMap.get(strategyMode);
    }
}
