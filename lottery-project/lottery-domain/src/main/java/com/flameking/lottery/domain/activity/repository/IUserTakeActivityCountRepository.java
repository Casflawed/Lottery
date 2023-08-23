package com.flameking.lottery.domain.activity.repository;

import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;

public interface IUserTakeActivityCountRepository {
    boolean subtractionLeftCount(ActivityBillVO bill);
}
