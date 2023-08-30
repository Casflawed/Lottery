package com.flameking.lottery.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 规则树支实体类
 *
 * @author WangWei
 * @dateTime 2023-08-27 17:22:30
 */
@Data
public class RuleTreeNodeLine{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 规则树ID */
    private Long treeId;

    /** 节点From */
    private Long nodeIdFrom;

    /** 节点To */
    private Long nodeIdTo;

    /** 限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围];7:果实 */
    private Integer ruleLimitType;

    /** 限定值 */
    private String ruleLimitValue;


}
