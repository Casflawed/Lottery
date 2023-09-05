package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.UserStrategyExport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户策略计算结果服务层接口
 *
 * @author WangWei
 * @dateTime 2023-08-11 22:31:53
 */
public interface IUserStrategyExportService extends IService<UserStrategyExport> {

//     IPage<UserStrategyExport> getPage(UserStrategyExport001Query query);


    UserStrategyExport findById(Long id);

    Long create(UserStrategyExport userStrategyExport);

    boolean update(UserStrategyExport userStrategyExport);

    boolean del(String id);

    /**
     * 更新发货单MQ状态
     *
     * @param uId     用户ID
     * @param orderId 订单ID
     * @param mqState MQ 发送状态
     * @return
     */
    boolean updateInvoiceMqState(String uId, Long orderId, Integer mqState);

    /**
     * 更新发奖状态
     *
     * @return
     */
    boolean updateUserAwardState(String uId, Long orderId, Long awardId, Integer grantState);

    /**
     * 扫描发货单 MQ 状态，把超时30分钟未发送 MQ 和发送 MQ 失败的单子扫描出来，做补偿
     *
     * @return 发货单
     */
    List<UserStrategyExport> scanInvoiceMqState();
}

