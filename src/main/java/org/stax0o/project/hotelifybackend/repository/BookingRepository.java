package org.stax0o.project.hotelifybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long id);

    List<Booking> findByRoomId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE " +
            "b.room.hotel.id = :hotelId AND " +
            "b.startDate < :today AND " +
            "b.paymentStatus = 'UNPAID'")
    void deleteOverdueUnpaidBookingsByHotel(@Param("hotelId") Long hotelId,
                                            @Param("today") LocalDate today);

    @Query("SELECT b FROM Booking b WHERE b.endDate > :endDateAfter AND b.room.hotel.id = :hotelId")
    List<Booking> findActiveBookingsByHotel(@Param("endDateAfter") LocalDate endDateAfter, @Param("hotelId") Long hotelId);
}
