package com.flameking.lottery.application.mq.producer;

import com.alibaba.fastjson.JSON;
import com.flameking.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import com.flameking.lottery.domain.activity.model.vo.InvoiceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * MQ 消息发送服务
 */
@Component
public class KafkaProducer {

    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * MQ主题：中奖发货单
     * linux
     * 启动zk：bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
     * 启动kafka：bin/kafka-server-start.sh -daemon config/server.properties
     * 创建topic：bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic lottery_invoice
     *
     * windows
     * 启动zk：D:\environment\kafka_2.12-3.5.1>bin\windows\zookeeper-server-start.bat config/zookeeper.properties
     * 启动kafaka：D:\environment\kafka_2.12-3.5.1>bin\windows\kafka-server-start.bat config/server.properties
     * 创建topic：bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic lottery_invoice
     */
    public static final String TOPIC_INVOICE = "lottery_invoice";

    /**
     * MQ主题：活动领取记录
     *
     * 创建topic：bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic lottery_activity_partake
     */
    public static final String TOPIC_ACTIVITY_PARTAKE = "lottery_activity_partake";

    /**
     * 发送中奖物品发货单消息
     *
     * @param invoice 发货单
     */
    public ListenableFuture<SendResult<String, Object>> sendLotteryInvoice(InvoiceVO invoice) {
        String objJson = JSON.toJSONString(invoice);
        logger.info("发送MQ消息 topic：{} bizId：{} message：{}", TOPIC_INVOICE, invoice.getuId(), objJson);
        return kafkaTemplate.send(TOPIC_INVOICE, objJson);
    }

    /**
     * 发送领取活动记录MQ
     *
     * @param activityPartakeRecord 领取活动记录
     */
    public ListenableFuture<SendResult<String, Object>> sendLotteryActivityPartakeRecord(ActivityPartakeRecordVO activityPartakeRecord) {
        String objJson = JSON.toJSONString(activityPartakeRecord);
        logger.info("发送MQ消息(领取活动记录) topic：{} bizId：{} message：{}", TOPIC_ACTIVITY_PARTAKE, activityPartakeRecord.getuId(), objJson);
        return kafkaTemplate.send(TOPIC_ACTIVITY_PARTAKE, objJson);
    }

}
