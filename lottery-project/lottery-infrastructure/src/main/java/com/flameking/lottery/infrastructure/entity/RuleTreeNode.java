package com.flameking.lottery.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 规则树节点实体类
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
@Data
public class RuleTreeNode{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 规则树ID */
    private Long treeId;

    /** 节点类型；1子叶、2果实 */
    private Integer nodeType;

    /** 节点值[nodeType=2]；果实值 */
    private String nodeValue;

    /** 规则Key */
    private String ruleKey;

    /** 规则描述 */
    private String ruleDesc;


}
