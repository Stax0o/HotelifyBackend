package org.stax0o.project.hotelifybackend.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping
    public User findByEmail(@RequestParam @Email(message = "Некорректный формат email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{email}")
    public void deleteByEmail(@PathVariable @Email(message = "Некорректный формат email") String email) {
        userService.deleteByEmail(email);
    }
}
