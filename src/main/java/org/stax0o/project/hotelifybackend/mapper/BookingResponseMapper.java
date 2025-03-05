package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.entity.Booking;
import org.stax0o.project.hotelifybackend.response.BookingResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingResponseMapper {
    @Mapping(source = "room.hotel.id", target = "hotelId")
    @Mapping(source = "room.hotel.name", target = "hotelName")
    @Mapping(source = "room.name", target = "roomName")
    BookingResponse toDTO(Booking booking);

    List<BookingResponse> toDTOList(List<Booking> bookings);
}
