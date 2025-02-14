package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.BalanceTopupDTO;
import org.stax0o.project.hotelifybackend.service.BalanceTopupService;

import java.util.List;

@RestController
@RequestMapping("api/topup")
@Validated
@RequiredArgsConstructor
public class BalanceTopupController {
    private final BalanceTopupService topupService;

    @PostMapping
    public BalanceTopupDTO create(@Valid @RequestBody BalanceTopupDTO topupDTO) {
        return topupService.create(topupDTO);
    }

    @GetMapping
    public List<BalanceTopupDTO> findAllTopUps(@RequestParam Long userId) {
        return topupService.findAllTopUpsByUserId(userId);
    }
}
