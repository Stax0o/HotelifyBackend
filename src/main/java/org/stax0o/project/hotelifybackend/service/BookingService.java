package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stax0o.project.hotelifybackend.dto.BookingDTO;
import org.stax0o.project.hotelifybackend.entity.Booking;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.enums.PaymentStatus;
import org.stax0o.project.hotelifybackend.mapper.BookingMapper;
import org.stax0o.project.hotelifybackend.repository.BookingRepository;
import org.stax0o.project.hotelifybackend.repository.RoomRepository;

import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    public BookingDTO create(BookingDTO bookingDTO) {
        if (bookingDTO.paymentStatus() != null) {
            throw new IllegalStateException("paymentStatus должен быть пустым при создании бронирования");
        } else if (bookingDTO.cost() != null) {
            throw new IllegalStateException("cost должен быть пустым при создании бронирования");
        }



        Optional<Room> optionalRoom = roomRepository.findById(bookingDTO.roomId());
        if (optionalRoom.isEmpty()){
            throw new IllegalArgumentException("Такой комнаты не существует");
        }

        Booking booking = bookingMapper.toEntity(bookingDTO);

        double pricePerNight = optionalRoom.get().getPrice();
        int countDays = Period.between(booking.getStartDate(), booking.getEndDate()).getDays();
        booking.setCost(countDays * pricePerNight);

        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    public BookingDTO findById(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isEmpty()) {
            throw new IllegalStateException("Бронирования с таким id не существует");
        }
        return bookingMapper.toDTO(optionalBooking.get());
    }

    public List<BookingDTO> findByUserId(Long id) {
        List<Booking> bookingList = bookingRepository.findByUserId(id);
        return bookingMapper.toDTOList(bookingList);
    }

    public List<BookingDTO> findByRoomId(Long id) {
        List<Booking> bookingList = bookingRepository.findByRoomId(id);
        return bookingMapper.toDTOList(bookingList);
    }

    public BookingDTO changePaymentStatusToPAID(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isEmpty()) {
            throw new IllegalStateException("Бронирования с таким id не существует");
        }
        Booking booking = optionalBooking.get();
        booking.setPaymentStatus(PaymentStatus.PAID);
        booking = bookingRepository.save(booking);
        return bookingMapper.toDTO(booking);
    }
}
