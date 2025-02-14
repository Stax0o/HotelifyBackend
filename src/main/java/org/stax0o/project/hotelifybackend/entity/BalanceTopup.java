package org.stax0o.project.hotelifybackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.stax0o.project.hotelifybackend.enums.PaymentMethod;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
