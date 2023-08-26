package com.flameking.lottery.domain.strategy.model.req;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DrawReq {
    private String uId;
    private Long strategyId;
    private Long uuid;
}
