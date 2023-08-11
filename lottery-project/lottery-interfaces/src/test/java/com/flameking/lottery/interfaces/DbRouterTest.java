package com.flameking.lottery.interfaces;

import com.alibaba.fastjson.JSON;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.ids.IIdGenerator;
import com.flameking.lottery.infrastructure.entity.UserStrategyExport;
import com.flameking.lottery.infrastructure.entity.UserTakeActivity;
import com.flameking.lottery.infrastructure.service.IUserStrategyExportService;
import com.flameking.lottery.infrastructure.service.IUserTakeActivityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DbRouterTest {
    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;
    @Autowired
    private IUserStrategyExportService userStrategyExportService;
    @Autowired
    private IUserTakeActivityService userTakeActivityService;

    @Test
    public void test_insert() {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setUId("Uhdgkw766120d");
        userStrategyExport.setActivityId(idGeneratorMap.get(Constants.Ids.ShortCode).nextId());
        userStrategyExport.setOrderId(idGeneratorMap.get(Constants.Ids.SnowFlake).nextId());
        userStrategyExport.setStrategyId(idGeneratorMap.get(Constants.Ids.RandomNumeric).nextId());
        userStrategyExport.setStrategyType(Constants.StrategyMode.SINGLE.getCode());
        userStrategyExport.setGrantType(1);
        userStrategyExport.setGrantDate(new Date());
        userStrategyExport.setGrantState(1);
        userStrategyExport.setAwardId(100L);
        userStrategyExport.setAwardType(Constants.AwardType.DESC.getCode());
        userStrategyExport.setAwardName("奖品1");
        userStrategyExport.setAwardContent("");
        userStrategyExport.setUuid(String.valueOf(userStrategyExport.getOrderId()));

        userStrategyExportService.create(userStrategyExport);
    }

    @Test
    public void test_UserTake_insert() {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUId("Uhdgkw766120d"); // 1库：Ukdli109op89oi 2库：Ukdli109op811d
        userTakeActivity.setTakeId(121019889410L);
        userTakeActivity.setActivityId(100001L);
        userTakeActivity.setActivityName("测试活动");
        userTakeActivity.setTakeDate(new Date());
        userTakeActivity.setTakeCount(10);
        userTakeActivity.setUuid("Uhdgkw766120d");

        userTakeActivityService.create(userTakeActivity);
    }
}
