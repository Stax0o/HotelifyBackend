package org.stax0o.project.hotelifybackend.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.stax0o.project.hotelifybackend.enums.PaymentStatus;

import java.time.LocalDate;

public record HotelBookingsDTO (
        Long id,

        String roomName,

        String userFirstName,

        String userLastName,

        String userPhone,

        @NotNull
        @FutureOrPresent(message = "Дата должна быть текущая или будущая")
        LocalDate startDate,

        @NotNull
        @FutureOrPresent(message = "Дата должна быть текущая или будущая")
        LocalDate endDate,

        Double cost,

        PaymentStatus paymentStatus) {
}