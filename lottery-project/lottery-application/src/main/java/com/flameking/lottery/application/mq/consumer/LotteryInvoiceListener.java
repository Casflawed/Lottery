package com.flameking.lottery.application.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.model.vo.InvoiceVO;
import com.flameking.lottery.domain.award.factory.SendGoodsFactory;
import com.flameking.lottery.domain.award.goods.ISendGoods;
import com.flameking.lottery.domain.award.model.req.GoodsReq;
import com.flameking.lottery.domain.award.model.res.AwardSenderRes;
import com.flameking.middleware.db.router.strategy.IDBRouterStrategy;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 *  中奖发货单监听消息
 */
@Component
public class LotteryInvoiceListener {

    private Logger logger = LoggerFactory.getLogger(LotteryInvoiceListener.class);

    @Autowired
    private IDBRouterStrategy dbRouter;
    @Autowired
    private SendGoodsFactory goodsFactory;

    @KafkaListener(topics = "lottery_invoice", groupId = "lottery")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());

        // 1. 判断消息是否存在
        if (!message.isPresent()) {
            return;
        }

        // 2. 处理 MQ 消息
        try {
            // 1. 转化对象（或者你也可以重写Serializer<T>）
            InvoiceVO invoiceVO = JSON.parseObject((String) message.get(), InvoiceVO.class);
            dbRouter.doRouter(invoiceVO.getuId());
            // 2. 获取发送奖品工厂，执行发奖
            ISendGoods distributionGoodsService = goodsFactory.getSendGoods(invoiceVO.getAwardType());
            AwardSenderRes distributionRes = distributionGoodsService.doSend(new GoodsReq(invoiceVO.getuId(), invoiceVO.getOrderId(), invoiceVO.getAwardId(), invoiceVO.getAwardName(), invoiceVO.getAwardContent()));
            // TODO: 2023/9/4 如果为false，ack.acknowledge没有执行，那么消费这会走哪个方法呢？或者根本不会走消费者方法
            Assert.isTrue(Constants.AwardState.SUCCESS.getCode().equals(distributionRes.getCode()), distributionRes.getInfo());

            // 3. 打印日志
            logger.info("消费MQ消息，完成 topic：{} bizId：{} 发奖结果：{}", topic, invoiceVO.getuId(), JSON.toJSONString(distributionRes));

            // 4. 消息消费完成
            ack.acknowledge();
        } catch (Exception e) {
            // 发奖环节失败，消息重试。所有到环节，发货、更新库，都需要保证幂等。
            logger.error("消费MQ消息，失败 topic：{} message：{}", topic, message.get());
            throw e;
        }
    }

}
