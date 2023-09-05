package com.flameking.lottery.domain.award.template.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.award.factory.SendGoodsFactory;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.model.req.GoodsReq;
import com.flameking.lottery.domain.award.model.res.AwardSenderRes;
import com.flameking.lottery.domain.award.template.IAwardSenderTemplate;
import com.flameking.lottery.domain.strategy.draw.IDrawTemplate;
import com.flameking.lottery.domain.strategy.model.req.DrawReq;
import com.flameking.lottery.domain.strategy.model.res.DrawResult;
import com.flameking.lottery.domain.strategy.model.vo.DrawAwardInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AwardSenderTemplateImpl implements IAwardSenderTemplate {
    @Autowired
    private IDrawTemplate drawTemplate;
    @Autowired
    private SendGoodsFactory goodsFactory;

    @Override
    public AwardSenderRes sendAward(String uId, Long strategyId, Long orderId) {
        //执行抽奖
        DrawResult drawResult = drawTemplate.doDraw(new DrawReq(uId, strategyId, null));
        if (drawResult.getDrawState().equals(Constants.DrawState.FAIL.getCode())){
            log.debug("用户-" + uId + "抽奖结果-" + drawResult.getDrawStateName());
            return new AwardSenderRes(uId, 1, "发奖失败");
        }
        //获取抽中的奖品信息
        DrawAwardInfo drawAwardInfo = drawResult.getDrawAwardInfo();

        //根据奖品类型执行不同的发奖（配送）方式
        Integer awardType = drawAwardInfo.getAwardType();
        ISendGoods sendGoods = goodsFactory.getSendGoods(awardType);

        //orderId 使用用户参与活动时生成
        GoodsReq req = new GoodsReq(uId, orderId, drawAwardInfo.getAwardId(), drawAwardInfo.getAwardName(), drawAwardInfo.getAwardContent());
        AwardSenderRes awardSenderRes = sendGoods.doSend(req);

        //返回发奖（配送）结果
        return awardSenderRes;

    }
}
