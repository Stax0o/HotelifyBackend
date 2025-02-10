package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.entity.BalanceTopup;
import org.stax0o.project.hotelifybackend.service.BalanceTopupService;

import java.util.List;

@RestController
@RequestMapping("api/topup")
@Validated
@RequiredArgsConstructor
public class BalanceTopupController {
    private final BalanceTopupService topupService;

    @PostMapping
    public BalanceTopup create(@Valid @RequestBody BalanceTopup topup) {
        return topupService.create(topup);
    }

    @GetMapping
    public List<BalanceTopup> findAllTopUps(@RequestParam Long userId) {
        return topupService.findAllTopUpsByUserId(userId);
    }
}
