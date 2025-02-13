package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.dto.BalanceTopupDTO;
import org.stax0o.project.hotelifybackend.entity.BalanceTopup;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalanceTopupMapper {
    @Mapping(source = "user.id", target = "userId")
    BalanceTopupDTO toDTO(BalanceTopup balanceTopup);

    @Mapping(source = "userId", target = "user.id")
    BalanceTopup toEntity(BalanceTopupDTO balanceTopupDTO);

    List<BalanceTopupDTO> toDTOList(List<BalanceTopup> balanceTopupList);

    List<BalanceTopup> toEntityList(List<BalanceTopupDTO> balanceTopupDTOList);
}
