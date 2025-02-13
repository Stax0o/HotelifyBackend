package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.RoomDTO;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("api/room")
@Validated
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public RoomDTO create(@Valid @RequestBody RoomDTO roomDTO) {
        return roomService.create(roomDTO);
    }

    @GetMapping("{id}")
    public RoomDTO findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping
    public List<RoomDTO> findByHotelId(@RequestParam Long hotelId) {
        return roomService.findByHotelId(hotelId);
    }

    @PutMapping
    public RoomDTO update(@Valid @RequestBody RoomDTO roomDTO) {
        return roomService.update(roomDTO);
    }
}
