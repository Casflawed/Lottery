package com.flameking.lottery.domain.activity.service.workflow.state.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;

public class DoingState implements IActivityState {
    @Override
    public void doEdit(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动运行中不可编辑");

    }

    @Override
    public void doArraignment(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动运行中不可提审");

    }

    @Override
    public void doRevoke(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动运行中不可撤审");

    }

    @Override
    public void doPass(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动运行中不可通过");

    }

    @Override
    public void doDoing(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动已运行");

    }

    @Override
    public void doRefuse(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动运行中不可拒绝");

    }

    @Override
    public void doClose(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.CLOSE));

    }

    @Override
    public void doOpen(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动运行中不可开启");

    }

    @Override
    public Constants.ActivityState getName() {
        return Constants.ActivityState.DOING;
    }
}
