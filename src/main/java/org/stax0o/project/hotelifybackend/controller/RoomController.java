package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public Room create(@Valid @RequestBody Room room) {
        return roomService.create(room);
    }

    @GetMapping("{id}")
    public Room findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping
    public List<Room> findByHotelId(@RequestParam Long hotelId) {
        return roomService.findByHotelId(hotelId);
    }

    @PutMapping
    public Room update(@Valid @RequestBody Room room) {
        return roomService.update(room);
    }
}
