package org.stax0o.project.hotelifybackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoomDTO(
        Long id,

        @NotBlank
        @Size(min = 2, max = 100, message = "Название должно содержать от 2 до 100 символов")
        String name,

        Long hotelId,

        @DecimalMin(value = "1.0", message = "Минимальная стоимость - 1")
        Double price
) {
}
