package com.flameking.lottery.interfaces.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;

import com.flameking.lottery.application.process.IActivityProcess;
import com.flameking.lottery.application.process.req.DrawProcessReq;
import com.flameking.lottery.application.process.res.DrawProcessResult;
import com.flameking.lottery.application.process.res.RuleQuantificationCrowdResult;
import com.flameking.lottery.common.Constants;
import com.flameking.lottery.common.Result;
import com.flameking.lottery.domain.rule.model.req.DecisionMatterReq;
import com.flameking.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.flameking.lottery.infrastructure.entity.Activity;
import com.flameking.lottery.infrastructure.mapper.ActivityMapper;
import com.flameking.lottery.interfaces.assembler.IMapping;
import com.flameking.lottery.rpc.IActivityBooth;
import com.flameking.lottery.rpc.dto.ActivityDto;
import com.flameking.lottery.rpc.dto.AwardDTO;
import com.flameking.lottery.rpc.req.ActivityReq;
import com.flameking.lottery.rpc.req.DrawReq;
import com.flameking.lottery.rpc.req.QuantificationDrawReq;
import com.flameking.lottery.rpc.res.ActivityRes;
import com.flameking.lottery.rpc.res.DrawRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


/**
 * 抽奖活动展台实现类
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
@Service
@Slf4j
public class ActivityBooth extends ServiceImpl<ActivityMapper, Activity> implements IActivityBooth {

    @Resource
    private ActivityMapper activityMapper;
    @Autowired
    private IActivityProcess activityProcess;
    @Resource
    private IMapping<DrawAwardInfo, AwardDTO> awardMapping;

    @Override
    public ActivityRes queryActivityById(ActivityReq req) {

        Activity activity = getOne(new LambdaQueryWrapper<Activity>().eq(Activity::getActivityId, req.getActivityId()));

        ActivityDto activityDto = new ActivityDto();
        BeanUtils.copyProperties(activity,activityDto);

//        activityDto.setActivityId(activity.getActivityId());
//        activityDto.setActivityName(activity.getActivityName());
//        activityDto.setActivityDesc(activity.getActivityDesc());
//        activityDto.setBeginDateTime(activity.getBeginDateTime());
//        activityDto.setEndDateTime(activity.getEndDateTime());
//        activityDto.setStockCount(activity.getStockCount());
//        activityDto.setTakeCount(activity.getTakeCount());

        return new ActivityRes(new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), activityDto);
    }

    @Override
    public Long create(ActivityDto activityDto) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDto, activity);
        save(activity);
        return activity.getActivityId();
    }

    @Override
    public DrawRes doDraw(DrawReq drawReq) {
        try {
            log.info("抽奖，开始 uId：{} activityId：{}", drawReq.getuId(), drawReq.getActivityId());

            // 1. 执行抽奖
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(drawReq.getuId(), drawReq.getActivityId()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())) {
                log.error("抽奖，失败(抽奖过程异常) uId：{} activityId：{}", drawReq.getuId(), drawReq.getActivityId());
                return new DrawRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }

            // 2. 数据转换
            DrawAwardInfo drawAwardVO = drawProcessResult.getDrawAwardInfo();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
            awardDTO.setActivityId(drawReq.getActivityId());

            // 3. 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            log.info("抽奖，完成 uId：{} activityId：{} drawRes：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawRes));

            return drawRes;
        } catch (Exception e) {
            log.error("抽奖，失败 uId：{} activityId：{} reqJson：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawReq), e);
            return new DrawRes(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }

    @Override
    public DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq) {
        try {
            log.info("量化人群抽奖，开始 uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());

            // 1. 执行规则引擎，获取用户可以参与的活动号
            RuleQuantificationCrowdResult ruleQuantificationCrowdResult = activityProcess.doRuleQuantificationCrowd(new DecisionMatterReq(quantificationDrawReq.getTreeId(), quantificationDrawReq.getuId(), quantificationDrawReq.getValMap()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(ruleQuantificationCrowdResult.getCode())) {
                log.error("量化人群抽奖，失败(规则引擎执行异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(ruleQuantificationCrowdResult.getCode(), ruleQuantificationCrowdResult.getInfo());
            }

            // 2. 执行抽奖
            Long activityId = ruleQuantificationCrowdResult.getActivityId();
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(quantificationDrawReq.getuId(), activityId));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())) {
                log.error("量化人群抽奖，失败(抽奖过程异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }

            // 3. 数据转换
            DrawAwardInfo drawAwardVO = drawProcessResult.getDrawAwardInfo();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
            awardDTO.setActivityId(activityId);

            // 4. 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            log.info("量化人群抽奖，完成 uId：{} treeId：{} drawRes：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(drawRes));

            return drawRes;
        } catch (Exception e) {
            log.error("量化人群抽奖，失败 uId：{} treeId：{} reqJson：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(quantificationDrawReq), e);
            return new DrawRes(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }


    //    @Override
//    public IPage<Activity> getPage(ActivityQuery query){
//        Page<Activity> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }
//
//    @Override
//    public Activity findById(String id) {
//        return getById(id);
//    }
//
//    @Override
//    public Long create(Activity activity) {
//        activity.setCreator("wangwei");
//        activity.setCreateTime(LocalDateTime.now());
//        save(activity);
//        return activity.getId();
//    }
//
//    @Override
//    public boolean update(Activity activity) {
//        return updateById(activity);
//    }
//
//    @Override
//    public boolean del(String id) {
//        return removeById(id);
//    }


}

