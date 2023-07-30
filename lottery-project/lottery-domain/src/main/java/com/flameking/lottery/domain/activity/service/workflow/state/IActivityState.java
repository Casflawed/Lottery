package com.flameking.lottery.domain.activity.service.workflow.state;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.machine.ActivityStateMachine;

public interface IActivityState {
    /**
     * 编辑
     *
     * @param stateMachine
     */
    void doEdit(ActivityStateMachine stateMachine);

    /**
     * 提审
     * @param stateMachine
     */
    void doArraignment(ActivityStateMachine stateMachine);

    /**
     * 撤审
     *
     * @param stateMachine
     */
    void doRevoke(ActivityStateMachine stateMachine);

    /**
     * 通过
     *
     * @param stateMachine
     */
    void doPass(ActivityStateMachine stateMachine);

    /**
     * 运行中
     *
     * @param stateMachine
     */
    void doDoing(ActivityStateMachine stateMachine);

    /**
     * 拒绝
     *
     * @param stateMachine
     */
    void doRefuse(ActivityStateMachine stateMachine);

    /**
     * 关闭
     *
     * @param stateMachine
     */
    void doClose(ActivityStateMachine stateMachine);

    /**
     * 开启
     *
     * @param stateMachine
     */
    void doOpen(ActivityStateMachine stateMachine);

    Constants.ActivityState getName();
}

