package com.flameking.lottery.domain.activity.model.res;

import com.flameking.lottery.common.Result;
import lombok.Getter;
import lombok.Setter;

/**
 * 活动参与结果
 */
@Setter
@Getter
public class PartakeResult extends Result {

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 领取单id
     */
    private Long takeId;

    /** 库存 */
    private Integer stockCount;

    /** activity 库存剩余 */
    private Integer stockSurplusCount;

    public PartakeResult(String code, String info) {
        super(code, info);
    }

    public PartakeResult(String code, String info, Long strategyId, Long takeId) {
        super(code, info);
        this.strategyId = strategyId;
        this.takeId = takeId;
    }
}