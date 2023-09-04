package com.flameking.lottery.domain.award.goods;

import com.flameking.lottery.domain.award.repository.IAwardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 配送货物基础共用类
 */
public class AwardSenderBase {

    protected Logger logger = LoggerFactory.getLogger(AwardSenderBase.class);

    @Resource
    private IAwardRepository awardRepository;

    protected void updateUserAwardState(String uId, Long orderId, Long awardId, Integer grantState) {
        awardRepository.updateUserAwardState(uId, orderId, awardId, grantState);
    }

}
