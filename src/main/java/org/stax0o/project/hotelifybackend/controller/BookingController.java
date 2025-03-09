package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.BookingDTO;
import org.stax0o.project.hotelifybackend.dto.HotelBookingsDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.BookingMapper;
import org.stax0o.project.hotelifybackend.response.BookingResponse;
import org.stax0o.project.hotelifybackend.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("api/booking")
@Validated
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public BookingDTO create(@Valid @RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal User user) {
        return bookingService.create(bookingMapper.toEntity(bookingDTO), user);
    }

    @GetMapping("/my")
    public List<BookingResponse> getBookingsCurrentUser(@AuthenticationPrincipal User user) {
        return bookingService.findByUserId(user.getId());
    }

    @PutMapping("{id}")
    public BookingDTO changePaymentStatusToPAID(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return bookingService.changePaymentStatusToPAID(id, user);
    }

    @GetMapping
    public List<HotelBookingsDTO> getBookingsByHotelId(@AuthenticationPrincipal User user,
                                                       @RequestParam Long hotelId) {
        return bookingService.findByHotelId(user, hotelId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBooking(@AuthenticationPrincipal User user,
                                                @PathVariable Long id) {
        try {
            bookingService.deleteBooking(user, id);
            return ResponseEntity.ok("Бронирование успешно удалено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при удалении бронирования");
        }
    }
}
