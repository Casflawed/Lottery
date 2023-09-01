package com.flameking.lottery.interfaces;

import com.flameking.lottery.application.mq.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Kafka 消息测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTest {

    private Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void test_send() throws InterruptedException {
        // 发送消息
        while (true){
            kafkaProducer.send("hello,I am Lottery");
            Thread.sleep(3500);
        }
    }
}
