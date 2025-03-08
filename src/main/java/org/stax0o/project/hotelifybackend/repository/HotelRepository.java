package org.stax0o.project.hotelifybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.stax0o.project.hotelifybackend.entity.Hotel;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByUserId(Long id);

    @Query("SELECT DISTINCT h FROM Hotel h JOIN Room r ON r.hotel.id = h.id WHERE r.price > 0 AND r.isDelete = false")
    List<Hotel> findHotelsWithRoomsPricedAboveZero();
}
