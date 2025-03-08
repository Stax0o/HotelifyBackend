package org.stax0o.project.hotelifybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.stax0o.project.hotelifybackend.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelIdAndIsDeleteFalse(Long hotelId);

    @Query("SELECT r FROM Room r WHERE " +
            "r.hotel.id = :hotelId AND " +
            "r.isDelete = false AND " +
            "NOT EXISTS (SELECT b FROM Booking b WHERE " +
            "           b.room = r AND " +
            "           b.startDate < :endDate AND " +
            "           b.endDate > :startDate AND " +
            "           b.endDate >= :today)")
    List<Room> findAvailableRooms(
            @Param("hotelId") Long hotelId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("today") LocalDate today
    );

    List<Room> findByNameAndIsDeleteFalse(String name);
}