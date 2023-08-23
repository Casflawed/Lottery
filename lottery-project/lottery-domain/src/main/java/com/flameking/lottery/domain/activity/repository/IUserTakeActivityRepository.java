package com.flameking.lottery.domain.activity.repository;

import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;

public interface IUserTakeActivityRepository {
    boolean takeActivity(PartakeReq partake, ActivityBillVO bill, Long takeId);
}
