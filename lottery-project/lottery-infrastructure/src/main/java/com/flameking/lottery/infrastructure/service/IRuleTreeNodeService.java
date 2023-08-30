package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.RuleTreeNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 【请填写功能名称】服务层接口
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
public interface IRuleTreeNodeService extends IService<RuleTreeNode> {

//     IPage<RuleTreeNode> getPage(RuleTreeNodeQuery query);

     RuleTreeNode findById(String id);

     Long create(RuleTreeNode ruleTreeNode);

     boolean update(RuleTreeNode ruleTreeNode);

     boolean del(String id);

     List<RuleTreeNode> queryRuleTreeNodeList(Long treeId);
}

