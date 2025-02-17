package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.BookingDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.enums.UserRole;
import org.stax0o.project.hotelifybackend.mapper.BookingMapper;
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

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookingDTO findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<BookingDTO> findByUserIdOrRoomId(@RequestParam(required = false) Long userId,
                                                 @RequestParam(required = false) Long roomId,
                                                 @AuthenticationPrincipal User user) {
        if (userId != null && user.getUserRole() == UserRole.ADMIN) {
            return bookingService.findByUserId(userId);
        } else if (roomId != null && user.getUserRole() == UserRole.OWNER) {
            return bookingService.findByRoomId(roomId, user);
        } else {
            throw new IllegalArgumentException("Необходимо ввести id пользователя или комнаты");
        }
    }

    @PutMapping("{id}")
    public BookingDTO changePaymentStatusToPAID(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return bookingService.changePaymentStatusToPAID(id, user);
    }
}
