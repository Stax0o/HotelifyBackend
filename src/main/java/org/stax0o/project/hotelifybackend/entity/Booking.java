package org.stax0o.project.hotelifybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.stax0o.project.hotelifybackend.enums.PaymentStatus;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    @FutureOrPresent(message = "Дата должна быть текущая или будущая")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent(message = "Дата должна быть текущая или будущая")
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @DecimalMin(value = "1.0", message = "Стоимость должна быть положительной")
    private Double cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
