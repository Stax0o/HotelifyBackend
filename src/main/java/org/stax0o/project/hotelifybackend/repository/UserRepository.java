package org.stax0o.project.hotelifybackend.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.stax0o.project.hotelifybackend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@NotBlank(message = "Email не должен быть пустым") @Email(message = "Некорректный формат email") String email);

    void deleteByEmail(@NotBlank(message = "Email не должен быть пустым") @Email(message = "Некорректный формат email") String email);
}
