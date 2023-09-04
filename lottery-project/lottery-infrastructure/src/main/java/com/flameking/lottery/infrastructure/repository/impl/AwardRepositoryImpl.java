package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.domain.award.repository.IAwardRepository;
import com.flameking.lottery.infrastructure.service.IUserStrategyExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AwardRepositoryImpl implements IAwardRepository {
    @Autowired
    private IUserStrategyExportService userStrategyExportService;
    @Override
    public void updateUserAwardState(String uId, Long orderId, Long awardId, Integer grantState) {
        boolean isSuccess = userStrategyExportService.updateUserAwardState(uId, orderId, awardId, grantState);
    }
}
