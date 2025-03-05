package org.stax0o.project.hotelifybackend.response;

import org.stax0o.project.hotelifybackend.enums.PaymentStatus;

import java.time.LocalDate;

public record BookingResponse(
        Long id,

        Long hotelId,

        String hotelName,

        String roomName,

        LocalDate startDate,

        LocalDate endDate,

        Double cost,

        PaymentStatus paymentStatus) {
}
