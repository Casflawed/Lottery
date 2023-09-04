package com.flameking.lottery.interfaces;

import com.alibaba.fastjson.JSON;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import com.flameking.lottery.domain.activity.model.req.ActivityConfigReq;
import com.flameking.lottery.domain.activity.model.vo.ActivityVO;
import com.flameking.lottery.domain.activity.model.vo.AwardVO;
import com.flameking.lottery.domain.activity.model.vo.StrategyDetailVO;
import com.flameking.lottery.domain.activity.model.vo.StrategyVO;
import com.flameking.lottery.domain.activity.service.deploy.IActivityDeploy;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;
import com.flameking.lottery.domain.award.model.res.AwardSenderRes;
import com.flameking.lottery.domain.award.template.IAwardSenderTemplate;
import com.flameking.lottery.domain.strategy.draw.IDrawTemplate;
import com.flameking.lottery.domain.strategy.model.req.DrawReq;
import com.flameking.lottery.domain.strategy.model.res.DrawResult;
import com.flameking.lottery.rpc.IActivityBooth;
import com.flameking.lottery.rpc.dto.ActivityDto;
import com.flameking.lottery.rpc.req.ActivityReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WangWei
 * @dateTime 2023/6/20 16:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiTest {
    private ActivityConfigRich activityConfigRich;

    /**
     * TODO：后面编写ID生成策略
     */
    private Long activityId = 120981321L;

    @Reference(interfaceClass = IActivityBooth.class)
    IActivityBooth activityBooth;

    @Autowired
    private IDrawTemplate drawTemplate;
    @Autowired
    private IAwardSenderTemplate awardSenderTemplate;
    @Autowired
    private IActivityDeploy activityDeploy;
    @Autowired
    private ActivityStateMachine activityStateMachine;

    @Test
    public void test_insert() {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setActivityId(100001L);
        activityDto.setActivityName("618限时特惠活动");
        activityDto.setActivityDesc("活动期间，所有参加活动的商品均享受低至6折的特惠折扣，数量有限，先到先得。此次活动参与商品包括手机、电脑、智能家居等多个品类。快来抢购吧！");
        activityDto.setBeginDateTime(new Date());
        activityDto.setEndDateTime(new Date());
        activityDto.setStockCount(1000);
        activityDto.setTakeCount(1);
        activityDto.setState(0);
        activityBooth.create(activityDto);
    }

    @Test
    public void test_select() {
        ActivityReq req = new ActivityReq();
        req.setActivityId(100001L);

        log.info("测试结果：{}", JSON.toJSONString(activityBooth.queryActivityById(req).getActivity()));
    }


    @Test
    public void test_draw() {
        Long strategyId = 1001L;
        for (int i = 0; i < 100; ++i) {
            DrawResult drawResult = drawTemplate.doDraw(new DrawReq());
            System.out.println(JSON.toJSONString(drawResult));
        }

    }

    @Test
    public void test_send() {
        Long strategyId = 1001L;
        Long uId = 1001L;
        Long orderId = 3981247023891L;
        for (int i = 0; i < 100; i++){
            AwardSenderRes awardSenderRes = awardSenderTemplate.sendAward(String.valueOf(uId), strategyId, orderId);
            System.out.println(JSON.toJSONString(awardSenderRes));
        }

    }

    @Before
    public void init() {

        ActivityVO activity = new ActivityVO();
        activity.setActivityId(activityId);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("测试活动描述");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(Constants.ActivityState.EDIT.getCode());
        activity.setCreator("xiaofuge");

        AwardVO award_01 = new AwardVO();
        award_01.setAwardId("101");
        award_01.setAwardType(Constants.AwardType.DESC.getCode());
        award_01.setAwardName("电脑");
        award_01.setAwardContent("请联系活动组织者 fustack");

        AwardVO award_02 = new AwardVO();
        award_02.setAwardId("102");
        award_02.setAwardType(Constants.AwardType.DESC.getCode());
        award_02.setAwardName("手机");
        award_02.setAwardContent("请联系活动组织者 fustack");

        AwardVO award_03 = new AwardVO();
        award_03.setAwardId("103");
        award_03.setAwardType(Constants.AwardType.DESC.getCode());
        award_03.setAwardName("平板");
        award_03.setAwardContent("请联系活动组织者 fustack");

        AwardVO award_04 = new AwardVO();
        award_04.setAwardId("104");
        award_04.setAwardType(Constants.AwardType.DESC.getCode());
        award_04.setAwardName("耳机");
        award_04.setAwardContent("请联系活动组织者 fustack");

        AwardVO award_05 = new AwardVO();
        award_05.setAwardId("105");
        award_05.setAwardType(Constants.AwardType.DESC.getCode());
        award_05.setAwardName("数据线");
        award_05.setAwardContent("请联系活动组织者 fustack");

        List<AwardVO> awardList = new ArrayList<>();
        awardList.add(award_01);
        awardList.add(award_02);
        awardList.add(award_03);
        awardList.add(award_04);
        awardList.add(award_05);

        StrategyVO strategy = new StrategyVO();
        strategy.setStrategyId(10002L);
        strategy.setStrategyDesc("抽奖策略");
        strategy.setStrategyMode(Constants.StrategyMode.SINGLE.getCode());
        strategy.setGrantType(1);
        strategy.setGrantDate(new Date());
        strategy.setExtInfo("");

        StrategyDetailVO strategyDetail_01 = new StrategyDetailVO();
        strategyDetail_01.setStrategyId(strategy.getStrategyId());
        strategyDetail_01.setAwardId("101");
        strategyDetail_01.setAwardName("一等奖");
        strategyDetail_01.setAwardCount(10);
        strategyDetail_01.setAwardSurplusCount(10);
        strategyDetail_01.setAwardRate(new BigDecimal("0.05"));

        StrategyDetailVO strategyDetail_02 = new StrategyDetailVO();
        strategyDetail_02.setStrategyId(strategy.getStrategyId());
        strategyDetail_02.setAwardId("102");
        strategyDetail_02.setAwardName("二等奖");
        strategyDetail_02.setAwardCount(20);
        strategyDetail_02.setAwardSurplusCount(20);
        strategyDetail_02.setAwardRate(new BigDecimal("0.15"));

        StrategyDetailVO strategyDetail_03 = new StrategyDetailVO();
        strategyDetail_03.setStrategyId(strategy.getStrategyId());
        strategyDetail_03.setAwardId("103");
        strategyDetail_03.setAwardName("三等奖");
        strategyDetail_03.setAwardCount(50);
        strategyDetail_03.setAwardSurplusCount(50);
        strategyDetail_03.setAwardRate(new BigDecimal("0.20"));

        StrategyDetailVO strategyDetail_04 = new StrategyDetailVO();
        strategyDetail_04.setStrategyId(strategy.getStrategyId());
        strategyDetail_04.setAwardId("104");
        strategyDetail_04.setAwardName("四等奖");
        strategyDetail_04.setAwardCount(100);
        strategyDetail_04.setAwardSurplusCount(100);
        strategyDetail_04.setAwardRate(new BigDecimal("0.25"));

        StrategyDetailVO strategyDetail_05 = new StrategyDetailVO();
        strategyDetail_05.setStrategyId(strategy.getStrategyId());
        strategyDetail_05.setAwardId("104");
        strategyDetail_05.setAwardName("五等奖");
        strategyDetail_05.setAwardCount(500);
        strategyDetail_05.setAwardSurplusCount(500);
        strategyDetail_05.setAwardRate(new BigDecimal("0.35"));

        List<StrategyDetailVO> strategyDetailList = new ArrayList<>();
        strategyDetailList.add(strategyDetail_01);
        strategyDetailList.add(strategyDetail_02);
        strategyDetailList.add(strategyDetail_03);
        strategyDetailList.add(strategyDetail_04);
        strategyDetailList.add(strategyDetail_05);

        strategy.setStrategyDetailList(strategyDetailList);

        activityConfigRich = new ActivityConfigRich(activity, strategy, awardList);
    }

    @Test
    public void test_createActivity() {
        activityDeploy.createActivity(new ActivityConfigReq(activityId, activityConfigRich));
    }

    @Test
    public void test_alterState() {
        activityStateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.EDIT));
        log.debug("提交审核：" + activityStateMachine.doArraignment(100001L));
        log.debug("审核通过：" + activityStateMachine.doPass(100001L));
        log.debug("运行活动：" + activityStateMachine.doDoing(100001L));
        log.debug("二次提审：" + activityStateMachine.doPass(100001L));
    }

}
