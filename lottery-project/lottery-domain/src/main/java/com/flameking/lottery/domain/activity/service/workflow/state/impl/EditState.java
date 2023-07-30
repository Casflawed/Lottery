package com.flameking.lottery.domain.activity.service.workflow.state.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;

public class EditState implements IActivityState {
    @Override
    public void doEdit(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动编辑中");

    }

    @Override
    public void doArraignment(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.ARRAIGNMENT));
    }

    @Override
    public void doRevoke(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动编辑不可撤审");

    }

    @Override
    public void doPass(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动编辑不可通过");

    }

    @Override
    public void doDoing(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动编辑不可运行");

    }

    @Override
    public void doRefuse(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动编辑不可拒绝");

    }

    @Override
    public void doClose(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.CLOSE));
    }

    @Override
    public void doOpen(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动编辑不可开启");

    }

    @Override
    public Constants.ActivityState getName() {
        return Constants.ActivityState.EDIT;
    }
}
