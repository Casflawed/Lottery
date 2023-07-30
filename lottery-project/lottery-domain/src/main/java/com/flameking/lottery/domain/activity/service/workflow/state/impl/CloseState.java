package com.flameking.lottery.domain.activity.service.workflow.state.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;

public class CloseState implements IActivityState {
    @Override
    public void doEdit(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动关闭不可编辑");

    }

    @Override
    public void doArraignment(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动关闭不可提审");

    }

    @Override
    public void doRevoke(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动关闭不可撤审");

    }

    @Override
    public void doPass(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动关闭不可通过");

    }

    @Override
    public void doDoing(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动关闭不可运行");

    }

    @Override
    public void doRefuse(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动关闭不可拒绝");

    }

    @Override
    public void doClose(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动已关闭");

    }

    @Override
    public void doOpen(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.OPEN));
    }

    @Override
    public Constants.ActivityState getName() {
        return Constants.ActivityState.CLOSE;
    }
}
