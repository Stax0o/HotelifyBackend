package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.dto.HotelBookingsDTO;
import org.stax0o.project.hotelifybackend.entity.Booking;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelBookingsMapper {
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.phone", target = "userPhone")
    HotelBookingsDTO toDTO(Booking booking);

    List<HotelBookingsDTO> toDTOList(List<Booking> bookings);
}
