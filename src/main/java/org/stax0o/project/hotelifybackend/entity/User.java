package org.stax0o.project.hotelifybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.stax0o.project.hotelifybackend.enums.UserRole;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @Column(name = "first_name")
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Длина строки должна быть от 2 до 50 символов")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 50, message = "Длина строки должна быть от 2 до 50 символов")
    private String lastName;

    @NotNull(message = "Роль пользователя не должна быть null")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @NotBlank(message = "Телефон не должен быть пустым")
    @Size(min = 5, max = 20, message = "Длина строки должна быть от 5 до 20 символов")
    private String phone;

    private Double balance = 0.0;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
