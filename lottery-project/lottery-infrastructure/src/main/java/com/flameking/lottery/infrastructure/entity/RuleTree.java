package com.flameking.lottery.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 规则树实体类
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
@Data
public class RuleTree{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 规则树Id */
    private String treeName;

    /** 规则树描述 */
    private String treeDesc;

    /** 规则树根ID */
    private Long treeRootNodeId;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;


}
