package org.stax0o.project.hotelifybackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stax0o.project.hotelifybackend.dto.UserDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.UserMapper;
import org.stax0o.project.hotelifybackend.service.UserService;

@Validated
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

//    @GetMapping
//    public UserDTO findByIdOrEmail(
//            @RequestParam(required = false) Long id,
//            @RequestParam(required = false) @Email(message = "Некорректный формат email") String email) {
//
//        if (id != null) {
//            return userService.findById(id);
//        } else if (email != null) {
//            return userService.findByEmail(email);
//        } else {
//            throw new IllegalArgumentException("Необходимо передать email или id");
//        }
//    }

//    @GetMapping("/all")
//    public List<UserDTO> findAll() {
//        return userService.findAll();
//    }
//
//    @PutMapping
//    @PreAuthorize("#userDTO.id() == authentication.principal.id")
//    public UserDTO update(@Valid @RequestBody UserDTO userDTO) {
//        return userService.update(userDTO);
//    }

//todo реализовать удаление
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id) {
//        userService.deleteById(id);
//    }
}
