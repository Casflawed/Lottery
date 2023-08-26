package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flameking.lottery.infrastructure.entity.StrategyDetail;
import com.flameking.lottery.infrastructure.mapper.StrategyDetailMapper;
import com.flameking.lottery.infrastructure.service.IStrategyDetailService;
import com.flameking.lottery.infrastructure.vo.request.borrower.StrategyDetailReq;
import com.flameking.lottery.infrastructure.vo.response.borrower.StrategyDetailRes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 策略明细 服务实现类
 * </p>
 *
 * @author root
 * @since 2023-07-19 22:20:52
 */
@Service
public class StrategyDetailServiceImpl extends ServiceImpl<StrategyDetailMapper, StrategyDetail> implements IStrategyDetailService {

  @Autowired
  private StrategyDetailMapper strategyDetailMapper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveOrUpdate(StrategyDetailReq strategyDetailReq) {
    StrategyDetail strategyDetail = new StrategyDetail();
    BeanUtils.copyProperties(strategyDetailReq, strategyDetail);

    // 新增
    if (strategyDetailReq.getId() != null){
      save(strategyDetail);
    }
    // 编辑
    else {
      updateById(strategyDetail);
    }
    return true;
  }

  @Override
  public List<StrategyDetailRes> getStrategyDetailListByBasicId(String basicId) {
    List<StrategyDetailRes> resList = new ArrayList<>();

    //查询符合条件的策略明细
    LambdaQueryWrapper<StrategyDetail> lqw = new LambdaQueryWrapper<>();
    List<StrategyDetail> queryList = list(lqw);

    if (CollectionUtils.isNotEmpty(queryList)) {
      for (StrategyDetail strategyDetail : queryList) {
        StrategyDetailRes strategyDetailRes = new StrategyDetailRes();
        BeanUtils.copyProperties(strategyDetail, strategyDetailRes);

        resList.add(strategyDetailRes);
      }
    }
    return resList;
  }

  @Override
  public StrategyDetailRes getStrategyDetailById(String id) {
    StrategyDetail queryOne = getById(id);
    if (queryOne != null){
      StrategyDetailRes res = new StrategyDetailRes();
      BeanUtils.copyProperties(queryOne, res);

      return res;
    }
    return null;
  }

  @Override
  public boolean del(String id) {
    return removeById(id);
  }

  @Override
  public List<StrategyDetail> getStrategyDetailsByStrategyId(Long strategyId) {
    List<StrategyDetail> strategyDetails = list(new LambdaQueryWrapper<StrategyDetail>()
            .eq(StrategyDetail::getStrategyId, strategyId));

    return strategyDetails;
  }

  @Override
  public List<Long> queryAwardIdsWithoutAmount(Long strategyId) {
    List<StrategyDetail> strategyDetails = list(new LambdaQueryWrapper<StrategyDetail>()
            .eq(StrategyDetail::getStrategyId, strategyId)
            .eq(StrategyDetail::getAwardSurplusCount, 0));
    if (CollectionUtils.isNotEmpty(strategyDetails)){
      return strategyDetails.stream().map(StrategyDetail::getAwardId).collect(Collectors.toList());
    }else {
      return new ArrayList<Long>();
    }
  }

  @Override
  public boolean decreaseLeftAwardCount(Long strategyId, Long awardId) {
    LambdaUpdateWrapper<StrategyDetail> updateWrapper = new LambdaUpdateWrapper<>();
    //数据库行锁，解决并发问题
    updateWrapper.setSql("award_surplus_count = award_surplus_count - 1")
            .eq(StrategyDetail::getStrategyId, strategyId)
            .eq(StrategyDetail::getAwardId, awardId)
            //大于0的条件避免抽奖失败率高的情况
            .gt(StrategyDetail::getAwardSurplusCount, 0);
    return update(updateWrapper);
  }

}

