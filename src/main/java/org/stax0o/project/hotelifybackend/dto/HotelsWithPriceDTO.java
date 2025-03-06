package org.stax0o.project.hotelifybackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record HotelsWithPriceDTO(
        Long id,

        List<String> imagePaths,

        @NotBlank(message = "Название не должно быть пустым")
        String name,

        @NotBlank(message = "Город не может быть пустым")
        @Size(min = 2, max = 50, message = "Название города должно содержать от 2 до 50 символов")
        String city,

        Double minPrice
) {
}