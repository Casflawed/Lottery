package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.RuleTreeNode;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.RuleTreeNodeMapper;
import com.flameking.lottery.infrastructure.service.IRuleTreeNodeService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 【请填写功能名称】服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
@Service
public class RuleTreeNodeServiceImpl extends ServiceImpl<RuleTreeNodeMapper, RuleTreeNode> implements IRuleTreeNodeService {

    @Resource
    private RuleTreeNodeMapper ruleTreeNodeMapper;


//    @Override
//    public IPage<RuleTreeNode> getPage(RuleTreeNodeQuery query){
//        Page<RuleTreeNode> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<RuleTreeNode> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }

    @Override
    public RuleTreeNode findById(String id) {
        return getById(id);
    }

    @Override
    public Long create(RuleTreeNode ruleTreeNode) {
        save(ruleTreeNode);
        return ruleTreeNode.getId();
    }

    @Override
    public boolean update(RuleTreeNode ruleTreeNode) {
        return updateById(ruleTreeNode);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

    @Override
    public List<RuleTreeNode> queryRuleTreeNodeList(Long treeId) {
        return list(new LambdaQueryWrapper<RuleTreeNode>().eq(RuleTreeNode::getTreeId, treeId));
    }

}

