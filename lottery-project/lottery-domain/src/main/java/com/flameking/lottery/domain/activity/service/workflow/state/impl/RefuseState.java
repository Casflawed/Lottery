package com.flameking.lottery.domain.activity.service.workflow.state.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;

public class RefuseState implements IActivityState {
    @Override
    public void doEdit(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动拒绝不可编辑");

    }

    @Override
    public void doArraignment(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动拒绝不可提审");

    }

    @Override
    public void doRevoke(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.REVOKE));

    }

    @Override
    public void doPass(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动拒绝不可同通过");

    }

    @Override
    public void doDoing(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动拒绝不可运行");

    }

    @Override
    public void doRefuse(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动已拒绝");

    }

    @Override
    public void doClose(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.CLOSE));

    }

    @Override
    public void doOpen(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动拒绝不可开启");

    }

    @Override
    public Constants.ActivityState getName() {
        return Constants.ActivityState.REFUSE;
    }
}
