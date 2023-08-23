package com.flameking.lottery.interfaces;

import com.alibaba.fastjson.JSON;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.service.partake.IActivityPartake;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityTest {
    @Autowired
    private IActivityPartake activityPartake;

    @Test
    public void test_activityPartake() {
        PartakeReq req = new PartakeReq("Uhdgkw766120d", 100001L);
        PartakeResult res = activityPartake.doPartake(req);
        log.info("请求参数：{}", JSON.toJSONString(req));
        log.info("测试结果：{}", JSON.toJSONString(res));
    }
}
