package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.BalanceTopupDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.service.BalanceTopupService;

import java.util.List;

@RestController
@RequestMapping("api/topup")
@Validated
@RequiredArgsConstructor
public class BalanceTopupController {
    private final BalanceTopupService topupService;

    @PostMapping
    public BalanceTopupDTO create(@Valid @RequestBody BalanceTopupDTO topupDTO,
                                  @AuthenticationPrincipal User user) {
        return topupService.create(topupDTO, user);
    }

    @GetMapping
    public List<BalanceTopupDTO> findAllUserTopUps(@AuthenticationPrincipal User user) {
        return topupService.findAllTopUpsByUserId(user.getId());
    }
}
