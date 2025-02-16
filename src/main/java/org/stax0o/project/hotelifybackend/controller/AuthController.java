package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stax0o.project.hotelifybackend.dto.JwtAuthenticationDTO;
import org.stax0o.project.hotelifybackend.dto.SignInDTO;
import org.stax0o.project.hotelifybackend.dto.SignUpDTO;
import org.stax0o.project.hotelifybackend.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationDTO signUp(@RequestBody @Valid SignUpDTO request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationDTO signIn(@RequestBody @Valid SignInDTO request) {
        return authenticationService.signIn(request);
    }
}
