package com.flameking.lottery.domain.activity.service.deploy;

import com.flameking.lottery.domain.activity.model.req.ActivityConfigReq;
import com.flameking.lottery.domain.activity.model.vo.ActivityVO;

import java.util.List;

public interface IActivityDeploy {
    /**
     * 创建活动
     */
    void createActivity(ActivityConfigReq req);

    List<ActivityVO> scanToDoActivityList(Long id);
}
