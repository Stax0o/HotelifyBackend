package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
                          @RequestParam Integer count) {
        return roomService.create(roomDTO, user, count);
    }

    @GetMapping("{id}")
    public RoomDTO findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping
    public List<RoomDTO> findByHotelId(@RequestParam Long hotelId) {
        return roomService.findByHotelId(hotelId);
    }

    @GetMapping("/available-types")
    public List<RoomTypeDTO> getAvailableRoomTypes(@RequestParam Long hotelId,
                                                   @RequestParam LocalDate startDate,
                                                   @RequestParam LocalDate endDate) {
        return roomService.getAvailableRoomTypes(hotelId, startDate, endDate);
    }

//    @PutMapping
//    public RoomDTO update(@Valid @RequestBody RoomDTO roomDTO) {
//        return roomService.update(roomDTO);
//    }
}
