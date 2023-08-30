package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.RuleTree;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 【请填写功能名称】服务层接口
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
public interface IRuleTreeService extends IService<RuleTree> {

//     IPage<RuleTree> getPage(RuleTreeQuery query);

     RuleTree findById(String id);

     Long create(RuleTree ruleTree);

     boolean update(RuleTree ruleTree);

     boolean del(String id);

     RuleTree queryRuleTreeByTreeId(Long treeId);
}

