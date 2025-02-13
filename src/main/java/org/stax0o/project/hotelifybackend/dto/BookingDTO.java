package org.stax0o.project.hotelifybackend.dto;

import org.stax0o.project.hotelifybackend.enums.PaymentStatus;

import java.time.LocalDate;

public record BookingDTO(Long id, Long userId, Long roomId, LocalDate startDate, LocalDate endDate, Double cost,
                         PaymentStatus paymentStatus) {
}
