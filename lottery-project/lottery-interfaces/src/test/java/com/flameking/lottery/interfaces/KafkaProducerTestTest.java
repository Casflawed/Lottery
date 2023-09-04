package com.flameking.lottery.interfaces;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Kafka 消息测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTestTest {

    private Logger logger = LoggerFactory.getLogger(KafkaProducerTestTest.class);

    @Autowired
    private com.flameking.lottery.application.mq.KafkaProducerTest kafkaProducer;

    @Test
    public void test_send() throws InterruptedException {
        // 发送消息
        while (true){
            kafkaProducer.send("hello,I am Lottery");
            Thread.sleep(3500);
        }
    }
}
