package org.stax0o.project.hotelifybackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HotelDTO(
        Long id,

        @NotNull(message = "id пользователя не должен быть пустым")
        Long userId,

        @NotBlank(message = "Название не должно быть пустым")
        String name,

        @NotBlank(message = "Описание не должно быть пустым")
        String description,

        @NotBlank(message = "Город не может быть пустым")
        @Size(min = 2, max = 50, message = "Название города должно содержать от 2 до 50 символов")
        String city,

        @NotBlank(message = "Адрес не может быть пустым")
        String address,

        @NotBlank(message = "Телефон не должен быть пустым")
        @Size(min = 5, max = 20, message = "Длина телефона должна быть от 5 до 20 символов")
        String phone,

        @NotBlank(message = "Email не должен быть пустым")
        @Email(message = "Некорректный формат email")
        String email
) {
}