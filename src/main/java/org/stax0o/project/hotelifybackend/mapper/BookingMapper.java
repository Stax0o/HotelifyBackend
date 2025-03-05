package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.dto.BookingDTO;
import org.stax0o.project.hotelifybackend.entity.Booking;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "room.id", target = "roomId")
    BookingDTO toDTO(Booking booking);

    @Mapping(source = "roomId", target = "room.id")
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "cost", ignore = true)
    Booking toEntity(BookingDTO bookingDTO);

    List<BookingDTO> toDTOList(List<Booking> bookings);

    List<Booking> toEntityList(List<BookingDTO> bookingDTOs);
}
