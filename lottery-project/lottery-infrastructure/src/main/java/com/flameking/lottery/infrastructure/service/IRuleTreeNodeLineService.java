package com.flameking.lottery.infrastructure.service;

import com.flameking.lottery.infrastructure.entity.RuleTreeNodeLine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 【请填写功能名称】服务层接口
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
public interface IRuleTreeNodeLineService extends IService<RuleTreeNodeLine> {

//     IPage<RuleTreeNodeLine> getPage(RuleTreeNodeLineQuery query);

     RuleTreeNodeLine findById(String id);

     Long create(RuleTreeNodeLine ruleTreeNodeLine);

     boolean update(RuleTreeNodeLine ruleTreeNodeLine);

     boolean del(String id);

     List<RuleTreeNodeLine> queryRuleTreeNodeLineList(RuleTreeNodeLine ruleTreeNodeLineReq);
}

