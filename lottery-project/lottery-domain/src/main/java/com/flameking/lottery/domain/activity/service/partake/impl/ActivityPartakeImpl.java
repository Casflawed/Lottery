package com.flameking.lottery.domain.activity.service.partake.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.StockResult;
import com.flameking.lottery.domain.activity.model.vo.*;
import com.flameking.lottery.domain.activity.repository.IActivityRepository;
import com.flameking.lottery.domain.activity.repository.IUserTakeActivityCountRepository;
import com.flameking.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.flameking.lottery.domain.activity.service.partake.BaseActivityPartake;
import com.flameking.middleware.db.router.strategy.IDBRouterStrategy;
import com.flameking.middleware.db.router.support.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Slf4j
@Component
public class ActivityPartakeImpl extends BaseActivityPartake {
    @Autowired
    private IActivityRepository activityRepository;
    @Autowired
    private IDBRouterStrategy dbRouter;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private IUserTakeActivityCountRepository userTakeActivityCountRepository;
    @Autowired
    private IUserTakeActivityRepository userTakeActivityRepository;

    @Override
    protected void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        activityRepository.recoverActivityCacheStockByRedis(activityId, tokenKey, code);
    }

    @Override
    protected StockResult subtractionActivityStock(String uId, Long activityId, Integer stockCount){
        return activityRepository.subtractionActivityStockByRedis(uId, activityId, stockCount);
    }

    @Override
    protected Result subtractionActivityStock(PartakeReq req) {
        boolean data = activityRepository.subtractionActivityStock(req.getActivityId());
        if (!data) {
            log.error("扣减活动库存失败 activityId：{}", req.getActivityId());
            return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
        }
        return Result.buildSuccessResult();
    }


    @Override
    protected Result checkActivityBill(PartakeReq partake, ActivityBillVO bill) {
        // 校验：活动状态
        if (!Constants.ActivityState.DOING.getCode().equals(bill.getState())) {
            log.warn("活动当前状态非可用 state：{}", bill.getState());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动当前状态非可用");
        }

        // 校验：活动日期
        if (bill.getBeginDateTime().after(partake.getPartakeDate()) || bill.getEndDateTime().before(partake.getPartakeDate())) {
            log.warn("活动时间范围非可用 beginDateTime：{} endDateTime：{}", bill.getBeginDateTime(), bill.getEndDateTime());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动时间范围非可用");
        }

        // 校验：活动库存
        if (bill.getStockSurplusCount() <= 0) {
            log.warn("活动剩余库存非可用 stockSurplusCount：{}", bill.getStockSurplusCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动剩余库存非可用");
        }

        // 校验：个人库存 - 个人活动剩余可领取次数
        if (bill.getUserTakeLeftCount() != null && bill.getUserTakeLeftCount() <= 0) {
            log.warn("个人领取次数非可用 userTakeLeftCount：{}", bill.getUserTakeLeftCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "个人领取次数非可用");
        }

        return Result.buildSuccessResult();
    }

    @Override
    protected ActivityBillVO queryActivityBill(PartakeReq req) {
        return activityRepository.queryActivityBill(req);
    }

    @Override
    protected Result grabActivity(PartakeReq partake, ActivityBillVO bill, Long takeId) {
        // 用了这个就必须手动释放ThreadLocal内的路由信息
        dbRouter.doRouter(partake.getUId());
        try {
            return transactionTemplate.execute(status -> {
                try {
                    // 扣减个人已参与次数
                    boolean updateCount = userTakeActivityCountRepository.subtractionLeftCount(bill);
                    if (!updateCount) {
                        status.setRollbackOnly();
                        log.error("领取活动，扣减个人已参与次数失败 activityId：{} uId：{}", partake.getActivityId(), partake.getUId());
                        return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                    }
                    // 插入领取活动信息
                    userTakeActivityRepository.takeActivity(partake, bill, takeId);

                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("领取活动，唯一索引冲突 activityId：{} uId：{}", partake.getActivityId(), partake.getUId(), e);
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
                }
                return Result.buildSuccessResult();
            });
        } finally {
            DataSourceContextHolder.clear();
        }
    }

    @Override
    public Result recordDrawOrder(DrawOrderVO drawOrder) {
        try {
            dbRouter.doRouter(drawOrder.getUId());
            return transactionTemplate.execute(status -> {
                try {
                    // 锁定活动领取记录
                    boolean isSuccess = userTakeActivityRepository.lockTackActivity(drawOrder.getUId(), drawOrder.getActivityId(), drawOrder.getTakeId());
                    if (!isSuccess) {
                        status.setRollbackOnly();
                        log.error("记录中奖单，个人参与活动抽奖已消耗完 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getUId());
                        return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                    }

                    // 保存抽奖信息
                    userTakeActivityRepository.saveUserStrategyExport(drawOrder);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("记录中奖单，唯一索引冲突 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getUId(), e);
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
                }
                return Result.buildSuccessResult();
            });
        } finally {
            DataSourceContextHolder.clear();
        }

    }

    @Override
    protected UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        return userTakeActivityRepository.queryNoConsumedTakeActivityOrder(activityId, uId);
    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        userTakeActivityRepository.updateInvoiceMqState(uId, orderId, mqState);
    }

    @Override
    public boolean updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        return userTakeActivityRepository.updateActivityStock(activityPartakeRecordVO);
    }
    @Override
    public List<InvoiceVO> scanInvoiceMqState(int dbCount, int tbCount) {
        try {
            // 设置路由
            dbRouter.setDbKey(dbCount);
            dbRouter.setTbKey(tbCount);

            // 查询数据
            return userTakeActivityRepository.scanInvoiceMqState();
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}
