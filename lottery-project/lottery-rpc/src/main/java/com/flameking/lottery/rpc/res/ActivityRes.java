package com.flameking.lottery.rpc.res;

import com.flameking.lottery.common.Result;
import com.flameking.lottery.rpc.dto.ActivityDto;

import java.io.Serializable;

/**
 * @author WangWei
 * @dateTime 2023/6/20 16:19
 */
public class ActivityRes implements Serializable{
  private Result result;
  private ActivityDto activity;

  public ActivityRes() {
  }

  public ActivityRes(Result result) {
    this.result = result;
  }

  public ActivityRes(Result result, ActivityDto activity) {
    this.result = result;
    this.activity = activity;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public ActivityDto getActivity() {
    return activity;
  }

  public void setActivity(ActivityDto activity) {
    this.activity = activity;
  }


}
