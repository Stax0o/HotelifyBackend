package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(source = "user.id", target = "userId")
    HotelDTO toDTO(Hotel hotel);

    @Mapping(source = "userId", target = "user.id")
    Hotel toEntity(HotelDTO hotelDTO);

    List<HotelDTO> toDTOList(List<Hotel> hotels);

    List<Hotel> toEntityList(List<HotelDTO> hotelsDTOs);
}
