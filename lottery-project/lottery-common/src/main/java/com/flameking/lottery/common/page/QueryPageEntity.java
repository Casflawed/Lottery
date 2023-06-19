package com.flameking.lottery.common.page;

import lombok.Data;

/**
 * 分页查询封装参数
 */
@Data
public class QueryPageEntity {

    /**
     * 分页查询每页条数，默认10
     */
    private Integer pageSize = 10;
    /**
     * 分页查询第几页，默认1
     */
    private Integer pageNum = 1;
}
