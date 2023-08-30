package com.flameking.lottery.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.flameking.lottery.infrastructure.entity.RuleTreeNodeLine;
import javax.annotation.Resource;

import com.flameking.lottery.infrastructure.mapper.RuleTreeNodeLineMapper;
import com.flameking.lottery.infrastructure.service.IRuleTreeNodeLineService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 【请填写功能名称】服务层实现类
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
@Service
public class RuleTreeNodeLineServiceImpl extends ServiceImpl<RuleTreeNodeLineMapper, RuleTreeNodeLine> implements IRuleTreeNodeLineService {

    @Resource
    private RuleTreeNodeLineMapper ruleTreeNodeLineMapper;


//    @Override
//    public IPage<RuleTreeNodeLine> getPage(RuleTreeNodeLineQuery query){
//        Page<RuleTreeNodeLine> page = new Page<>(query.getPageNum(),query.getPageSize());
//        LambdaQueryWrapper<RuleTreeNodeLine> wrapper = new LambdaQueryWrapper<>();
//        //需要根据业务加上对应的条件
//        return page(page, wrapper);
//    }

    @Override
    public RuleTreeNodeLine findById(String id) {
        return getById(id);
    }

    @Override
    public Long create(RuleTreeNodeLine ruleTreeNodeLine) {
        save(ruleTreeNodeLine);
        return ruleTreeNodeLine.getId();
    }

    @Override
    public boolean update(RuleTreeNodeLine ruleTreeNodeLine) {
        return updateById(ruleTreeNodeLine);
    }

    @Override
    public boolean del(String id) {
        return removeById(id);
    }

    @Override
    public List<RuleTreeNodeLine> queryRuleTreeNodeLineList(RuleTreeNodeLine ruleTreeNodeLineReq) {
        return list(new LambdaQueryWrapper<RuleTreeNodeLine>()
                .eq(RuleTreeNodeLine::getTreeId, ruleTreeNodeLineReq.getTreeId())
                .eq(RuleTreeNodeLine::getNodeIdFrom, ruleTreeNodeLineReq.getNodeIdFrom()));
    }

}

