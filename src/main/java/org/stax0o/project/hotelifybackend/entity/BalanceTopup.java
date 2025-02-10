package org.stax0o.project.hotelifybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.stax0o.project.hotelifybackend.enums.PaymentMethod;
import org.stax0o.project.hotelifybackend.enums.PaymentStatus;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "balance_topup")
public class BalanceTopup {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Способ оплаты не может быть пустым")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @DecimalMin(value = "1.0")
    private Double amount;


    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
