package org.stax0o.project.hotelifybackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.stax0o.project.hotelifybackend.enums.UserRole;

public record SignUpDTO(
        @NotBlank(message = "Email не должен быть пустым")
        @Email(message = "Некорректный формат email")
        String email,

        @NotBlank(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 50, message = "Длина имени должна быть от 2 до 50 символов")
        String firstName,

        @NotBlank(message = "Фамилия не должна быть пустой")
        @Size(min = 2, max = 50, message = "Длина фамилии должна быть от 2 до 50 символов")
        String lastName,

        @NotNull(message = "Роль пользователя не должна быть null")
        UserRole userRole,

        @NotBlank(message = "Телефон не должен быть пустым")
        @Size(min = 5, max = 20, message = "Длина телефона должна быть от 5 до 20 символов")
        String phone,

        @NotBlank(message = "Пароль не должен быть пустым")
        String password
) {
}
