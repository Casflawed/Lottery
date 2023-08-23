package com.flameking.lottery.domain.activity.service.partake.impl;

import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.activity.model.aggregates.PartakeReq;
import com.flameking.lottery.domain.activity.model.res.PartakeResult;
import com.flameking.lottery.domain.activity.model.vo.ActivityBillVO;
import com.flameking.lottery.domain.activity.repository.IActivityRepository;
import com.flameking.lottery.domain.activity.repository.IUserTakeActivityCountRepository;
import com.flameking.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.flameking.lottery.domain.activity.service.partake.IActivityPartake;
import com.flameking.lottery.domain.ids.IIdGenerator;
import com.flameking.middleware.db.router.annotation.DbRouter;
import com.flameking.middleware.db.router.strategy.IDbRouterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

@Component
@Slf4j
public class ActivityPartakeImpl implements IActivityPartake {
    @Autowired
    private IActivityRepository activityRepository;
    @Autowired
    private IDbRouterStrategy doRouter;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private IUserTakeActivityCountRepository userTakeActivityCountRepository;
    @Autowired
    private IUserTakeActivityRepository userTakeActivityRepository;
    @Autowired
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public PartakeResult doPartake(PartakeReq req) {
        //查询活动信息和用户参与活动次数信息
        ActivityBillVO activityBillVO = queryActivityBill(req);

        //校验活动，包括活动状态、活动进行时间、活动可参与次数和用户个人剩余参与次数
        Result checkResult = checkActivityBill(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())) {
            return new PartakeResult(checkResult.getCode(), checkResult.getInfo());
        }
        //扣减活动剩余参与次数
        Result subtractionActivityResult = subtractionActivityStock(req);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(subtractionActivityResult.getCode())) {
            return new PartakeResult(subtractionActivityResult.getCode(), subtractionActivityResult.getInfo());
        }
        //更新or插入用户参与次数信息
        Result grabResult = grabActivity(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            return new PartakeResult(grabResult.getCode(), grabResult.getInfo());
        }

        //查询用户参与活动信息
        PartakeResult partakeResult = new PartakeResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        partakeResult.setStrategyId(activityBillVO.getStrategyId());
        return partakeResult;
    }

    private Result subtractionActivityStock(PartakeReq req) {
        boolean data = activityRepository.subtractionActivityStock(req.getActivityId());
        if (!data) {
            log.error("扣减活动库存失败 activityId：{}", req.getActivityId());
            return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
        }
        return Result.buildSuccessResult();
    }


    private Result checkActivityBill(PartakeReq partake, ActivityBillVO bill) {
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

    private ActivityBillVO queryActivityBill(PartakeReq req) {
        return activityRepository.queryActivityBill(req);
    }

    /**
     * 领取活动
     *
     * @param partake 参与活动请求
     * @param bill    活动账单
     * @return 领取结果
     */
    @DbRouter
    protected Result grabActivity(PartakeReq partake, ActivityBillVO bill) {
        // doRouter.doRouter(partake.getUId());，用了这个就必须手动释放ThreadLocal内的路由信息
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
                Long takeId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
                userTakeActivityRepository.takeActivity(partake, bill, takeId);

            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
                log.error("领取活动，唯一索引冲突 activityId：{} uId：{}", partake.getActivityId(), partake.getUId(), e);
                return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
            }
            return Result.buildSuccessResult();
        });
    }

}
