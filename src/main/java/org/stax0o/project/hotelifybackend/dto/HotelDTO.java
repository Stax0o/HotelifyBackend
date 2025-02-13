package org.stax0o.project.hotelifybackend.dto;

public record HotelDTO(Long id, Long userId, String name, String description, String city, String address, String phone,
                       String email) {
}
