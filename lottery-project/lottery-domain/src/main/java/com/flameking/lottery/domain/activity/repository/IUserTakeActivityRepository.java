package com.flameking.lottery.domain.activity.repository;

import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;
import com.flameking.lottery.domain.activity.model.vo.DrawOrderVO;

public interface IUserTakeActivityRepository {
    boolean takeActivity(PartakeReq partake, ActivityBillVO bill, Long takeId);

    boolean lockTackActivity(String uId, Long activityId, Long takeId);

    void saveUserStrategyExport(DrawOrderVO drawOrder);

}
