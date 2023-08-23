package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;
import com.flameking.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.flameking.lottery.infrastructure.entity.UserTakeActivity;
import com.flameking.lottery.infrastructure.service.IUserTakeActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTakeActivityRepository implements IUserTakeActivityRepository {
    @Autowired
    private IUserTakeActivityService userTakeActivityService;

    @Override
    public boolean takeActivity(PartakeReq partake, ActivityBillVO bill, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUId(bill.getUId());
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setActivityId(bill.getActivityId());
        userTakeActivity.setActivityName(bill.getActivityName());
        userTakeActivity.setTakeDate(partake.getPartakeDate());

        if (null == bill.getUserTakeLeftCount()) {
            userTakeActivity.setTakeCount(1);
        } else {
            userTakeActivity.setTakeCount(bill.getTakeCount() - bill.getUserTakeLeftCount());
        }
        String uuid = bill.getUId() + "_" + bill.getActivityId() + "_" + bill.getTakeCount();
        userTakeActivity.setUuid(uuid);

        return userTakeActivityService.create(userTakeActivity);
    }
}
