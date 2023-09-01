package com.flameking.lottery.interfaces.assembler;

import com.flameking.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.flameking.lottery.rpc.dto.AwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 对象转换配置
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AwardMapping extends IMapping<DrawAwardInfo, AwardDTO> {

//    @Mapping(target = "userId", source = "uId")
    @Override
    AwardDTO sourceToTarget(DrawAwardInfo var1);

    @Override
    DrawAwardInfo targetToSource(AwardDTO var1);

}
