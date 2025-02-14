package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.dto.RoomDTO;
import org.stax0o.project.hotelifybackend.entity.Room;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(source = "hotel.id", target = "hotelId")
    RoomDTO toDTO(Room room);

    @Mapping(source = "hotelId", target = "hotel.id")
    Room toEntity(RoomDTO roomDTO);

    List<RoomDTO> toDTOList(List<Room> roomList);

    List<Room> toEntityList(List<RoomDTO> roomDTOList);
}
