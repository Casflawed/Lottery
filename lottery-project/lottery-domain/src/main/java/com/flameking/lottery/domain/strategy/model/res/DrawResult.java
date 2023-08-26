package com.flameking.lottery.domain.strategy.model.res;

import com.flameking.lottery.domain.strategy.model.vo.DrawAwardInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 抽奖结果
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrawResult {

    /**
     * 用户ID
     */
    private String uId;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖 Constants.DrawState
     */
    private Integer drawState;

    private String drawStateName;

    /**
     *  中奖奖品信息
     */
    private DrawAwardInfo drawAwardInfo;

    public DrawResult(String uId, Long strategyId, Integer drawState, String drawStateName) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.drawState = drawState;
        this.drawStateName = drawStateName;
    }

}
