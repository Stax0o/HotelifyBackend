package org.stax0o.project.hotelifybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100, message = "Название должно содержать от 2 до 100 символов")
    private String name;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @DecimalMin(value = "1.0")
    private Double price;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
