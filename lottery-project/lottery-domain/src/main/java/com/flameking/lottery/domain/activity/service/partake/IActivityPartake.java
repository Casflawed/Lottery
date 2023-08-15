package com.flameking.lottery.domain.activity.service.partake;

import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;

public interface IActivityPartake {
    PartakeResult doPartake(PartakeReq req);
}
