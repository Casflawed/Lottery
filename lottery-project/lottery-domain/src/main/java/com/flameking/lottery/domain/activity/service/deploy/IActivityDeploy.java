package com.flameking.lottery.domain.activity.service.deploy;

import com.flameking.lottery.domain.activity.model.req.ActivityConfigReq;

public interface IActivityDeploy {
    /**
     * 创建活动
     */
    void createActivity(ActivityConfigReq req);
}
