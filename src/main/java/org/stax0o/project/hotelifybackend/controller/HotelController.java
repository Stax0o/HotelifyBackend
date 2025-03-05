package org.stax0o.project.hotelifybackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("api/hotel")
@Validated
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public HotelDTO create(@Valid @RequestPart("hotel") String hotelDTOJson, @RequestPart("images") List<MultipartFile> images) {

        ObjectMapper objectMapper = new ObjectMapper();
        HotelDTO hotelDTO;

        try {
            hotelDTO = objectMapper.readValue(hotelDTOJson, HotelDTO.class);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный формат JSON", e);
        }

        return hotelService.create(hotelDTO, images);
    }

    @GetMapping("{id}")
    public HotelDTO findById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping("/all")
    public List<HotelDTO> findAll() {
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

