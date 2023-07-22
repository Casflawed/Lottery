package com.flameking.lottery.interfaces;

import com.alibaba.fastjson.JSON;
import com.flameking.lottery.domain.strategy.draw.IDrawTemplate;
import com.flameking.lottery.infrastructure.entity.Award;
import com.flameking.lottery.rpc.IActivityBooth;
import com.flameking.lottery.rpc.dto.ActivityDto;
import com.flameking.lottery.rpc.req.ActivityReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author WangWei
 * @dateTime 2023/6/20 16:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiTest {


  @Reference(interfaceClass = IActivityBooth.class)
  IActivityBooth activityBooth;
  @Autowired
  private IDrawTemplate drawTemplate;

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
    for (int i = 0; i < 100; ++i){
      Award award = drawTemplate.doDraw(1001L, strategyId);
      if (award != null){
        System.out.println(award.getAwardName());
      }else {
        System.out.println("未中奖");
      }
    }

  }

}
