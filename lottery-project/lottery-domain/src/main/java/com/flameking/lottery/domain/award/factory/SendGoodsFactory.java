package com.flameking.lottery.domain.award.factory;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.goods.impl.CouponGoods;
import com.flameking.lottery.domain.award.goods.impl.DescGoods;
import com.flameking.lottery.domain.award.goods.impl.PhysicalGoods;
import com.flameking.lottery.domain.award.goods.impl.RedeemCodeGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 分发商品静态工厂类
 */
@Component
public class SendGoodsFactory {
    public final static Map<Integer, ISendGoods> sendGoodsMap = new HashMap<>();

    @Autowired
    private DescGoods descGoods;
    @Autowired
    private RedeemCodeGoods redeemCodeGoods;
    @Autowired
    private CouponGoods couponGoods;
    @Autowired
    private PhysicalGoods physicalGoods;

    @PostConstruct
    public void init() {
        sendGoodsMap.put(Constants.AwardType.DESC.getCode(), descGoods);
        sendGoodsMap.put(Constants.AwardType.RedeemCodeGoods.getCode(), new RedeemCodeGoods());
        sendGoodsMap.put(Constants.AwardType.CouponGoods.getCode(), new CouponGoods());
        sendGoodsMap.put(Constants.AwardType.PhysicalGoods.getCode(), new PhysicalGoods());

    }

    public ISendGoods getSendGoods(int strategyMode){
        return sendGoodsMap.get(strategyMode);
    }
}
