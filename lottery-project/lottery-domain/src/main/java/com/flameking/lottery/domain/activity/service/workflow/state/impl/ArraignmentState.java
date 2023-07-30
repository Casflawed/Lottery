package com.flameking.lottery.domain.activity.service.workflow.state.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;

public class ArraignmentState implements IActivityState {
    @Override
    public void doEdit(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动提审不可编辑");

    }

    @Override
    public void doArraignment(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动已提审");

    }

    @Override
    public void doRevoke(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(new RevokeState());
    }

    @Override
    public void doPass(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.PASS));

    }

    @Override
    public void doDoing(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动提审不可运行");

    }

    @Override
    public void doRefuse(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.REFUSE));

    }

    @Override
    public void doClose(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动提审不可关闭");

    }

    @Override
    public void doOpen(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动提审不可开启");

    }

    @Override
    public Constants.ActivityState getName() {
        return Constants.ActivityState.ARRAIGNMENT;
    }
}
