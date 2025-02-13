package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.BookingDTO;
import org.stax0o.project.hotelifybackend.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("api/booking")
@Validated
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDTO create(@Valid @RequestBody BookingDTO bookingDTO) {
        return bookingService.create(bookingDTO);
    }

    @GetMapping("{id}")
    public BookingDTO findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @GetMapping()
    public List<BookingDTO> findByUserIdOrRoomId(@RequestParam(required = false) Long userId,
                                                 @RequestParam(required = false) Long roomId) {
        if (userId != null) {
            return bookingService.findByUserId(userId);
        } else if (roomId != null) {
            return bookingService.findByRoomId(roomId);
        } else {
            throw new IllegalArgumentException("Необходимо ввести id пользователя или комнаты");
        }
    }

    @PutMapping("{id}")
    public BookingDTO changePaymentStatusToPAID(@PathVariable Long id) {
        return bookingService.changePaymentStatusToPAID(id);
    }
}
