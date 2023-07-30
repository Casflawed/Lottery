package com.flameking.lottery.domain.activity.service.workflow.machine;

import com.flameking.lottery.common.Constants;
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
    public boolean doEdit(Long activityId) {
        Assert.notNull(activityState, "activityState为null");
        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doEdit(this);

        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }

    /**
     * 提审
     */
    public boolean doArraignment(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doArraignment(this);

        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }

    /**
     * 撤审
     */
    public boolean doRevoke(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doRevoke(this);

        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }

    /**
     * 通过
     */
    public boolean doPass(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doPass(this);


        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }

    /**
     * 运行中
     */
    public boolean doDoing(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doDoing(this);

        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }

    /**
     * 拒绝
     */
    public boolean doRefuse(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doRefuse(this);

        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }

    /**
     * 关闭
     */
    public boolean doClose(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doClose(this);

        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }

    /**
     * 开启
     */
    public boolean doOpen(Long activityId) {
        Assert.notNull(activityState, "activityState为null");

        //获取当前状态
        Constants.ActivityState currentState = activityState.getName();

        //转换状态
        activityState.doOpen(this);

        //更新后动状态
        return activityRepository.alterStatus(activityId, currentState, activityState.getName());
    }
}
