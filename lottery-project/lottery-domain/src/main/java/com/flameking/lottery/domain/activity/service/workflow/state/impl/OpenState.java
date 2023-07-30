package com.flameking.lottery.domain.activity.service.workflow.state.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;

public class OpenState implements IActivityState {
    @Override
    public void doEdit(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动开启不可编辑");
    }

    @Override
    public void doArraignment(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动开启不可提审");
    }

    @Override
    public void doRevoke(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动开启不可撤审");

    }

    @Override
    public void doPass(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动开启不可通过");

    }

    @Override
    public void doDoing(ActivityStateMachine stateMachine) {
        //转移状态
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.DOING));
    }

    @Override
    public void doRefuse(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动开启不可拒绝");

    }

    @Override
    public void doClose(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.CLOSE));
    }

    @Override
    public void doOpen(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动已开启");

    }

    @Override
    public Constants.ActivityState getName() {
        return Constants.ActivityState.OPEN;
    }
}
