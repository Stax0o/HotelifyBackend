package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.stax0o.project.hotelifybackend.dto.UserDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.service.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDTO create(@Valid @RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @GetMapping
    public UserDTO findByIdOrEmail(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) @Email(message = "Некорректный формат email") String email) {

        if (id != null) {
            return userService.findById(id);
        } else if (email != null) {
            return userService.findByEmail(email);
        } else {
            throw new IllegalArgumentException("Необходимо передать email или id");
        }
    }

    @GetMapping("/all")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @PutMapping
    public UserDTO update(@Valid @RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
