package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("api/hotel")
@Validated
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasAuthority('OWNER')")
    public HotelDTO create(@Valid @RequestBody HotelDTO hotelDTO) {
        return hotelService.create(hotelDTO);
    }

    @GetMapping("{id}")
    public HotelDTO findById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping("/all")
    public List<HotelDTO> findAll(){
        return hotelService.findAll();
    }

    @GetMapping
    public List<HotelDTO> findByUserId(@RequestParam Long userId) {
        return hotelService.findByUserId(userId);
    }

    @PutMapping
    public HotelDTO update(@Valid @RequestBody HotelDTO hotelDTO) {
        return hotelService.update(hotelDTO);
    }
}

