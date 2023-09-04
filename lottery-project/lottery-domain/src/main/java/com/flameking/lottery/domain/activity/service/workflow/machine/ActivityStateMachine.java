package com.flameking.lottery.domain.activity.service.workflow.machine;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.repository.IActivityRepository;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityStateMachine {
    //当前状态
    private IActivityState activityState;
    @Resource
    private IActivityRepository activityRepository;

    /**
     * 编辑
     */
    public Result doEdit(Long activityId) {
        Assert.notNull(activityState, "activityState为null");
        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doEdit(this);

        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更编辑完成") : Result.buildErrorResult("活动状态变更失败");
    }

    /**
     * 提审
     */
    public Result doArraignment(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doArraignment(this);
        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更提审完成") : Result.buildErrorResult("活动状态变更失败");

    }

    /**
     * 撤审
     */
    public Result doRevoke(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doRevoke(this);

        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更撤审完成") : Result.buildErrorResult("活动状态变更失败");

    }

    /**
     * 通过
     */
    public Result doPass(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doPass(this);


        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更通过完成") : Result.buildErrorResult("活动状态变更失败");

    }

    /**
     * 运行中
     */
    public Result doDoing(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doDoing(this);

        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更运行中完成") : Result.buildErrorResult("活动状态变更失败");

    }

    /**
     * 拒绝
     */
    public Result doRefuse(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doRefuse(this);

        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更拒绝完成") : Result.buildErrorResult("活动状态变更失败");

    }

    /**
     * 关闭
     */
    public Result doClose(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doClose(this);

        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更关闭完成") : Result.buildErrorResult("活动状态变更失败");

    }

    /**
     * 开启
     */
    public Result doOpen(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doOpen(this);

        //更新后动状态
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, activityState.getName());

        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更开启完成") : Result.buildErrorResult("活动状态变更失败");

    }
}
