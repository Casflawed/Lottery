package com.flameking.lottery.domain.strategy.service;

import com.flameking.lottery.domain.strategy.entity.StrategyDetail;
import com.flameking.lottery.domain.strategy.vo.request.borrower.StrategyDetailReq;
import com.flameking.lottery.domain.strategy.vo.response.borrower.StrategyDetailRes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 策略明细 服务类
 * </p>
 *
 * @author root
 * @since 2023-07-19 22:20:52
 */
public interface IStrategyDetailService extends IService<StrategyDetail> {
  boolean saveOrUpdate(StrategyDetailReq strategyDetailReq);

  List<StrategyDetailRes> getStrategyDetailListByBasicId(String basicId);

  StrategyDetailRes getStrategyDetailById(String id);

  boolean del(String id);

  List<StrategyDetail> getStrategyDetailsByStrategyId(Long strategyId);

}
