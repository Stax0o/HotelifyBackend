package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.stax0o.project.hotelifybackend.dto.RoomDTO;
import org.stax0o.project.hotelifybackend.entity.Room;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO toDTO(Room room);

    Room toEntity(RoomDTO roomDTO);

    List<RoomDTO> toDTOList(List<Room> roomList);

    List<Room> toEntityList(List<RoomDTO> roomDTOList);
}
