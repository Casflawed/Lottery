package com.flameking.lottery.domain.activity.service.workflow.state.support;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.domain.activity.service.workflow.state.IActivityState;
import com.flameking.lottery.domain.activity.service.workflow.state.impl.*;

import java.util.HashMap;

public class ActivityStateSupport {
    public final static HashMap<Constants.ActivityState, IActivityState> activityStateMap = new HashMap<>();

    static {
        activityStateMap.put(Constants.ActivityState.EDIT, new EditState());
        activityStateMap.put(Constants.ActivityState.ARRAIGNMENT, new ArraignmentState());
        activityStateMap.put(Constants.ActivityState.REVOKE, new RevokeState());
        activityStateMap.put(Constants.ActivityState.DOING, new DoingState());
        activityStateMap.put(Constants.ActivityState.REFUSE, new RefuseState());
        activityStateMap.put(Constants.ActivityState.CLOSE, new CloseState());
        activityStateMap.put(Constants.ActivityState.OPEN, new OpenState());
        activityStateMap.put(Constants.ActivityState.PASS, new PassState());

    }

    public static IActivityState getActivityState(Constants.ActivityState activityState){
        return activityStateMap.get(activityState);
    }

}
