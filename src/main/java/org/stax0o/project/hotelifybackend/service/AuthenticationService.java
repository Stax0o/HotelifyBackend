package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.stax0o.project.hotelifybackend.dto.JwtAuthenticationDTO;
import org.stax0o.project.hotelifybackend.dto.LoginDTO;
import org.stax0o.project.hotelifybackend.dto.RegisterDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.SignUpMapper;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SignUpMapper signUpMapper;

    public JwtAuthenticationDTO signUp(RegisterDTO request) {
        User user = signUpMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationDTO(jwt);
    }

    public JwtAuthenticationDTO signIn(LoginDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.email());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationDTO(jwt);
    }
}
