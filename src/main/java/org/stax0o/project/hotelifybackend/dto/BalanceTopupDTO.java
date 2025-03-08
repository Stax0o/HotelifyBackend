package org.stax0o.project.hotelifybackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.stax0o.project.hotelifybackend.enums.PaymentMethod;

public record BalanceTopupDTO(
        Long id,

        Long userId,

        @NotNull(message = "Способ оплаты не может быть пустым")
        PaymentMethod paymentMethod,

        @NotNull(message = "Сумма пополнения не может быть пустой")
        @DecimalMin(value = "1.0", message = "Минимальная сумма пополнения - 1.0")
        Double amount
) {
}
