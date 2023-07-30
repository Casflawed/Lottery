package com.flameking.lottery.domain.activity.service.workflow.state.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.support.ActivityStateSupport;

public class RevokeState implements IActivityState {
    @Override
    public void doEdit(ActivityStateMachine stateMachine) {
        stateMachine.setActivityState(ActivityStateSupport.getActivityState(Constants.ActivityState.EDIT));

    }

    @Override
    public void doArraignment(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动撤审不可提审");
    }

    @Override
    public void doRevoke(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动已撤审");
    }

    @Override
    public void doPass(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动撤审不可通过");
    }

    @Override
    public void doDoing(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动撤审不可运行");
    }

    @Override
    public void doRefuse(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动撤审不可拒绝");
    }

    @Override
    public void doClose(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动撤审不可关闭");
    }

    @Override
    public void doOpen(ActivityStateMachine stateMachine) {
        throw new RuntimeException("活动撤审不可开启");
    }

    @Override
    public Constants.ActivityState getName() {
        return Constants.ActivityState.REVOKE;
    }
}
