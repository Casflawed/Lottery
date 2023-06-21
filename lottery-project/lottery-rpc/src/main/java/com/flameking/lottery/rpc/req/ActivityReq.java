package com.flameking.lottery.rpc.req;

import java.io.Serializable;

/**
 * @author WangWei
 * @dateTime 2023/6/20 16:20
 */
public class ActivityReq implements Serializable {
  private Long activityId;

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

}
