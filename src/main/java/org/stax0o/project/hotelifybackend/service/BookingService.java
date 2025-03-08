package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.dto.BookingDTO;
import org.stax0o.project.hotelifybackend.entity.Booking;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.enums.PaymentStatus;
import org.stax0o.project.hotelifybackend.mapper.BookingMapper;
import org.stax0o.project.hotelifybackend.mapper.BookingResponseMapper;
import org.stax0o.project.hotelifybackend.repository.BookingRepository;
import org.stax0o.project.hotelifybackend.repository.RoomRepository;
import org.stax0o.project.hotelifybackend.repository.UserRepository;
import org.stax0o.project.hotelifybackend.response.BookingResponse;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final BookingResponseMapper bookingResponseMapper;

    @Transactional
    public BookingDTO create(Booking booking, User user) {
        if (booking.getCost() != null) {
            throw new IllegalStateException("cost должен быть пустым при создании бронирования");
        }

        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Такой комнаты не существует"));

        boolean isAvailableRoom = roomRepository.findAvailableRooms(room.getHotel().getId(),
                        booking.getStartDate(),
                        booking.getEndDate(),
                        LocalDate.now())
                .stream()
                .anyMatch(roomItem -> Objects.equals(roomItem.getId(), booking.getRoom().getId()));

        if (!isAvailableRoom) {
            throw new IllegalArgumentException("Комната уже занята");
        }

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

    public List<BookingResponse> findByUserId(Long id) {
        List<Booking> bookingList = bookingRepository.findByUserId(id);
        return bookingResponseMapper.toDTOList(bookingList);
    }

    public List<BookingResponse> findByRoomId(Long id, User user) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Такой комнаты не существует"));
        Hotel hotel = room.getHotel();
        if (!Objects.equals(hotel.getId(), user.getId())) {
            throw new IllegalArgumentException("Комната не принадлежит данному пользователю");
        }
        List<Booking> bookingList = bookingRepository.findByRoomId(id);
        return bookingResponseMapper.toDTOList(bookingList);
    }

    @Transactional
    public BookingDTO changePaymentStatusToPAID(Long id, User user) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Бронирования с таким id не существует"));
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Нет прав на изменение статуса оплаты другого пользователя");
        }
        if (booking.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalArgumentException("Бронирование уже оплачено");
        }
        if (booking.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Бронирование просрочено");
        }

        updateBalance(user, booking.getCost(), true);
        updateBalance(booking.getRoom().getHotel().getUser(), booking.getCost(), false);

        booking.setPaymentStatus(PaymentStatus.PAID);

        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    public void deleteBooking(User user, Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Бронирования с таким id не существует"));
        if (!Objects.equals(booking.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Бронирование не принадлежит этому пользователю");
        }
        bookingRepository.delete(booking);
    }

    private void updateBalance(User user, double amount, boolean isSpent) {
        double newBalance = isSpent ? user.getBalance() - amount : user.getBalance() + amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("У пользователя недостаточно средств");
        }
        user.setBalance(newBalance);
        user.setUpdatedAt(LocalDate.now());
        userRepository.save(user);
    }
}
