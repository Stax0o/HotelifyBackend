package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.dto.UserDTO;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.UserMapper;
import org.stax0o.project.hotelifybackend.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO create(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.email());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("Пользователь с таким email уже существует");
        }

        User user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Пользователя с таким email не существует");
        }
        return userMapper.toDTO(optionalUser.get());
    }

    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Пользователя с таким id не существует");
        }
        return userMapper.toDTO(optionalUser.get());
    }

    public List<UserDTO> findAll() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    @Transactional
    public UserDTO update(UserDTO newUser) {
        Optional<User> optionalUser = userRepository.findByEmail(newUser.email());
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Пользователя с таким email не существует");
        }
        User user = optionalUser.get();
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
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Пользователя с таким id не существует");
        }
        userRepository.deleteById(id);
    }
}
