package org.stax0o.project.hotelifybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Название не должно быть пустым")
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    @NotBlank(message = "Город не может быть пустым")
    @Size(min = 2, max = 50, message = "Названии города должно содержать не меньше 2 символов и не больше 50")
    private String city;

    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    @NotBlank(message = "Телефон не должен быть пустым")
    @Size(min = 5, max = 20, message = "Длина строки должна быть от 5 до 20 символов")
    @Column(unique = true)
    private String phone;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    @Column(unique = true)
    private String email;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
