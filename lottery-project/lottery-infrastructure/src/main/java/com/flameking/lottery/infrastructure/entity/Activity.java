package com.flameking.lottery.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 活动配置实体类
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
@Data
public class Activity{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 活动ID */
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 活动描述 */
    private String activityDesc;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDateTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDateTime;

    /** 库存 */
    private Integer stockCount;

    /** 每人可参与次数 */
    private Integer takeCount;

    /** 活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启 */
    private Integer state;

    /** 创建人 */
    private String creator;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;


}
