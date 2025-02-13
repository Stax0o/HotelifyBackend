package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("api/hotel")
@Validated
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public HotelDTO create(@Valid @RequestBody HotelDTO hotelDTO) {
        return hotelService.create(hotelDTO);
    }

    @GetMapping("{id}")
    public Hotel findById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping
    public List<Hotel> findByUserId(@RequestParam Long userId) {
        return hotelService.findByUserId(userId);
    }

    @PutMapping
    public Hotel update(@Valid @RequestBody Hotel hotel) {
        return hotelService.update(hotel);
    }
}

