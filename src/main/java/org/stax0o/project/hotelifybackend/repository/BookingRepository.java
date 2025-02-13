package org.stax0o.project.hotelifybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stax0o.project.hotelifybackend.entity.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long id);

    List<Booking> findByRoomId(Long id);
}
