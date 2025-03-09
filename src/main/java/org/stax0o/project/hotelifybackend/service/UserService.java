package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.dto.UserDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.UserMapper;
import org.stax0o.project.hotelifybackend.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO create(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email занят");
        }
        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с таким email не существует"));
        return userMapper.toDTO(user);
    }

    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с email " + email + " не найден"));
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с таким id не существует"));
        return userMapper.toDTO(user);
    }

    public List<UserDTO> findAll() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    @Transactional
    public UserDTO update(UserDTO newUser) {
        User user = userRepository.findById(newUser.id())
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с таким id не существует"));
        user.setEmail(newUser.email());
        user.setFirstName(newUser.firstName());
        user.setLastName(newUser.lastName());
        user.setPhone(newUser.phone());
        user.setUpdatedAt(LocalDate.now());
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с таким id не существует"));
        userRepository.deleteById(id);
    }
}
