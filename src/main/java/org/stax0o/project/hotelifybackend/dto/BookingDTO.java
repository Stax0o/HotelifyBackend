package org.stax0o.project.hotelifybackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.stax0o.project.hotelifybackend.enums.PaymentStatus;

import java.time.LocalDate;

public record BookingDTO(
        Long id,

        Long userId,

        Long roomId,

        @NotNull
        @FutureOrPresent(message = "Дата должна быть текущая или будущая")
        LocalDate startDate,

        @NotNull
        @FutureOrPresent(message = "Дата должна быть текущая или будущая")
        LocalDate endDate,

        @NotNull
        @DecimalMin(value = "1.0", message = "Стоимость должна быть положительной")
        Double cost,

        PaymentStatus paymentStatus) {
}
