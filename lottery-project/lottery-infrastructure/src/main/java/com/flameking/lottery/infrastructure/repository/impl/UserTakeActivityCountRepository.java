package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;
import com.flameking.lottery.domain.activity.repository.IUserTakeActivityCountRepository;
import com.flameking.lottery.infrastructure.entity.UserTakeActivityCount;
import com.flameking.lottery.infrastructure.service.IUserTakeActivityCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTakeActivityCountRepository implements IUserTakeActivityCountRepository {
    @Autowired
    private IUserTakeActivityCountService userTakeActivityCountService;

    public boolean subtractionLeftCount(ActivityBillVO bill) {
        if (null == bill.getUserTakeLeftCount()) {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setUId(bill.getUId());
            userTakeActivityCount.setActivityId(bill.getActivityId());
            userTakeActivityCount.setTotalCount(bill.getTakeCount());
            userTakeActivityCount.setLeftCount(bill.getTakeCount() - 1);
            return userTakeActivityCountService.create(userTakeActivityCount);
        } else {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setUId(bill.getUId());
            userTakeActivityCount.setActivityId(bill.getActivityId());
            return userTakeActivityCountService.updateLeftCount(userTakeActivityCount);
        }
    }
}
