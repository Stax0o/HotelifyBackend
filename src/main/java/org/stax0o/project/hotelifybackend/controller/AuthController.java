package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stax0o.project.hotelifybackend.dto.JwtAuthenticationDTO;
import org.stax0o.project.hotelifybackend.dto.LoginDTO;
import org.stax0o.project.hotelifybackend.dto.RegisterDTO;
import org.stax0o.project.hotelifybackend.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public JwtAuthenticationDTO signUp(@RequestBody @Valid RegisterDTO request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationDTO signIn(@RequestBody @Valid LoginDTO request) {
        return authenticationService.signIn(request);
    }
}
