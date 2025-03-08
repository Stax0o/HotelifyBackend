package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.RoomDTO;
import org.stax0o.project.hotelifybackend.dto.RoomTypeDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.service.RoomService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/room")
@Validated
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @PreAuthorize("hasAuthority('OWNER')")
    public List<RoomDTO> create(@Valid @RequestBody RoomDTO roomDTO,
                                @AuthenticationPrincipal User user,
                                @RequestParam(required = false, defaultValue = "1") Integer count) {
        return roomService.create(roomDTO, user, count);
    }

    @GetMapping("{id}")
    public RoomDTO findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('OWNER')")
    public List<RoomTypeDTO> findByHotelId(@RequestParam Long hotelId,
                                           @AuthenticationPrincipal User user) {
        return roomService.findByHotelId(hotelId, user);
    }

    @GetMapping("/available-types")
    public List<RoomTypeDTO> getAvailableRoomTypes(@RequestParam Long hotelId,
                                                   @RequestParam LocalDate startDate,
                                                   @RequestParam LocalDate endDate) {
        return roomService.getAvailableRoomTypes(hotelId, startDate, endDate);
    }

    @PutMapping
    public ResponseEntity<String> update(@AuthenticationPrincipal User user,
                                         @RequestBody RoomTypeDTO newRoomTypeDTO) {
        try {
            roomService.update(user, newRoomTypeDTO);
            return ResponseEntity.ok("Комнаты успешно удалены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при удалении комнат");
        }
    }

}
