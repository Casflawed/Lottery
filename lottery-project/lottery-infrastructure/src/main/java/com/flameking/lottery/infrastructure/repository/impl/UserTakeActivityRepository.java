package com.flameking.lottery.infrastructure.repository.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.util.EntityUtils;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.vo.*;
import com.flameking.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.flameking.lottery.domain.activity.service.partake.IActivityPartake;
import com.flameking.lottery.infrastructure.entity.Activity;
import com.flameking.lottery.infrastructure.entity.UserStrategyExport;
import com.flameking.lottery.infrastructure.entity.UserTakeActivity;
import com.flameking.lottery.infrastructure.service.IActivityService;
import com.flameking.lottery.infrastructure.service.IUserStrategyExportService;
import com.flameking.lottery.infrastructure.service.IUserTakeActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserTakeActivityRepository implements IUserTakeActivityRepository {
    @Autowired
    private IUserTakeActivityService userTakeActivityService;
    @Autowired
    private IUserStrategyExportService userStrategyExportService;
    @Autowired
    private IActivityService activityService;

    @Override
    public boolean takeActivity(PartakeReq partake, ActivityBillVO bill, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUId(bill.getUId());
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setActivityId(bill.getActivityId());
        userTakeActivity.setActivityName(bill.getActivityName());
        userTakeActivity.setTakeDate(partake.getPartakeDate());

        if (null == bill.getUserTakeLeftCount()) {
            userTakeActivity.setTakeCount(1);
        } else {
            userTakeActivity.setTakeCount(bill.getTakeCount() - bill.getUserTakeLeftCount());
        }
        String uuid = bill.getUId() + "_" + bill.getActivityId() + "_" + userTakeActivity.getTakeCount();
        userTakeActivity.setUuid(uuid);
        userTakeActivity.setStrategyId(bill.getStrategyId());
        userTakeActivity.setState(Constants.TaskState.NO_USED.getCode());

        return userTakeActivityService.create(userTakeActivity);
    }

    @Override
    public boolean lockTackActivity(String uId, Long activityId, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUId(uId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setTakeId(takeId);
        return userTakeActivityService.update(userTakeActivity);
    }

    @Override
    public void saveUserStrategyExport(DrawOrderVO drawOrder) {
        UserStrategyExport export = new UserStrategyExport();
        EntityUtils.copyProperties(drawOrder, export);
        export.setUuid(String.valueOf(drawOrder.getOrderId()));
        export.setMqState(Constants.MQState.INIT.getCode());
        userStrategyExportService.create(export);
    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        boolean isSuccess = userStrategyExportService.updateInvoiceMqState(uId, orderId, mqState);
    }

    @Override
    public UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        UserTakeActivity noConsumedTakeActivityOrder = userTakeActivityService.queryNoConsumedTakeActivityOrder(activityId, uId);
        if (noConsumedTakeActivityOrder == null){
            return null;
        }

        UserTakeActivityVO userTakeActivityVO = new UserTakeActivityVO();
        EntityUtils.copyProperties(noConsumedTakeActivityOrder, userTakeActivityVO);

        return userTakeActivityVO;
    }

    @Override
    public boolean updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        Activity activity = new Activity();
        activity.setActivityId(activityPartakeRecordVO.getActivityId());
        activity.setStockSurplusCount(activityPartakeRecordVO.getStockSurplusCount());
        return activityService.updateActivityStock(activity);
    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState() {
        // 查询发送MQ失败和超时30分钟，未发送MQ的数据
        List<UserStrategyExport> userStrategyExportList = userStrategyExportService.scanInvoiceMqState();
        // 转换对象
        return EntityUtils.toList(userStrategyExportList, InvoiceVO::new);
    }
}
