package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.RuleTree;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.entity.RuleTreeNodeLine;
import com.flameking.lottery.infrastructure.mapper.RuleTreeMapper;
import com.flameking.lottery.infrastructure.service.IRuleTreeService;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
@Service
public class RuleTreeServiceImpl extends ServiceImpl<RuleTreeMapper, RuleTree> implements IRuleTreeService {

    @Resource
    private RuleTreeMapper ruleTreeMapper;


//    @Override
//    public IPage<RuleTree> getPage(RuleTreeQuery query){
//        Page<RuleTree> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<RuleTree> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }

    @Override
    public RuleTree findById(String id) {
        return getById(id);
    }

    @Override
    public Long create(RuleTree ruleTree) {
        save(ruleTree);
        return ruleTree.getId();
    }

    @Override
    public boolean update(RuleTree ruleTree) {
        return updateById(ruleTree);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

    @Override
    public RuleTree queryRuleTreeByTreeId(Long treeId) {
        return getById(treeId);
    }

}

