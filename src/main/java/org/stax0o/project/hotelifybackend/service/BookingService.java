package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.dto.BookingDTO;
import org.stax0o.project.hotelifybackend.entity.Booking;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.enums.PaymentStatus;
import org.stax0o.project.hotelifybackend.mapper.BookingMapper;
import org.stax0o.project.hotelifybackend.repository.BookingRepository;
import org.stax0o.project.hotelifybackend.repository.RoomRepository;
import org.stax0o.project.hotelifybackend.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public BookingDTO create(BookingDTO bookingDTO) {
        if (bookingDTO.paymentStatus() != null) {
            throw new IllegalStateException("paymentStatus должен быть пустым при создании бронирования");
        } else if (bookingDTO.cost() != null) {
            throw new IllegalStateException("cost должен быть пустым при создании бронирования");
        }

        User user = userRepository.findById(bookingDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("Такого пользователя не существует"));
        Room room = roomRepository.findById(bookingDTO.roomId())
                .orElseThrow(() -> new IllegalArgumentException("Такой комнаты не существует"));

        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking.setUser(user);
        booking.setRoom(room);

        double pricePerNight = room.getPrice();
        int countDays = Period.between(booking.getStartDate(), booking.getEndDate()).getDays();
        booking.setCost(countDays * pricePerNight);

        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    public BookingDTO findById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Бронирования с таким id не существует"));
        return bookingMapper.toDTO(booking);
    }

    public List<BookingDTO> findByUserId(Long id) {
        List<Booking> bookingList = bookingRepository.findByUserId(id);
        return bookingMapper.toDTOList(bookingList);
    }

    public List<BookingDTO> findByRoomId(Long id) {
        List<Booking> bookingList = bookingRepository.findByRoomId(id);
        return bookingMapper.toDTOList(bookingList);
    }

    @Transactional
    public BookingDTO changePaymentStatusToPAID(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Бронирования с таким id не существует"));

        updateBalance(booking.getUser().getId(), booking.getCost(), true);
        updateBalance(booking.getRoom().getHotel().getUser().getId(), booking.getCost(), false);

        booking.setPaymentStatus(PaymentStatus.PAID);

        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    private void updateBalance(Long userId, double amount, boolean isSpent){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Пользователя, который осуществлял бронирование, больше не существует"));
        double newBalance = isSpent ? user.getBalance() - amount : user.getBalance() + amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("У пользователя недостаточно средств");
        }
        user.setBalance(newBalance);
        user.setUpdatedAt(LocalDate.now());
        userRepository.save(user);
    }
}
