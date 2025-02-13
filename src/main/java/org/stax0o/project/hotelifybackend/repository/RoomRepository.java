package org.stax0o.project.hotelifybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stax0o.project.hotelifybackend.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelId(Long hotelId);
}
