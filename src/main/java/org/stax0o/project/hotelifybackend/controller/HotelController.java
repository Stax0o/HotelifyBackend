package org.stax0o.project.hotelifybackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.dto.HotelsWithPriceDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.service.HotelService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/hotel")
@Validated
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public HotelDTO create(@Valid @RequestPart("hotel") String hotelDTOJson,
                           //                           todo убрать value
                           @RequestPart("images") List<MultipartFile> images,
                           @AuthenticationPrincipal User user) {
        HotelDTO hotelDTO = jsonToDTO(hotelDTOJson);

        return hotelService.create(hotelDTO, images, user);
    }

    @GetMapping("{id}")
    public HotelDTO findById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping("/all")
    public List<HotelsWithPriceDTO> findAll(
            @RequestParam(required = false, defaultValue = "1970-01-01") LocalDate date,
            @RequestParam(required = false, defaultValue = "") String city,
            @RequestParam(required = false, defaultValue = "0.0") Double price) {
        return hotelService.findAll(date, city, price);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('OWNER')")
    public List<HotelDTO> findByUserId(@AuthenticationPrincipal User user) {
        return hotelService.findByUserId(user.getId());
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public HotelDTO update(@Valid @RequestPart("hotel") String hotelDTOJson,
//                           todo убрать value
                           @RequestPart(value = "images", required = false) List<MultipartFile> images,
                           @AuthenticationPrincipal User user) {
        HotelDTO hotelDTO = jsonToDTO(hotelDTOJson);
        return hotelService.update(hotelDTO, images, user);
    }

    private HotelDTO jsonToDTO(String hotelDTOJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        HotelDTO hotelDTO;

        try {
            hotelDTO = objectMapper.readValue(hotelDTOJson, HotelDTO.class);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный формат JSON", e);
        }
        return hotelDTO;
    }
}

