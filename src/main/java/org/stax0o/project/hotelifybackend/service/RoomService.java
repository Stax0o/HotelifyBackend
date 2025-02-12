package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public Room findById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            throw new IllegalStateException("Номера с таким id не существует");
        }
        return optionalRoom.get();
    }

    public List<Room> findByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public Room update(Room newRoom) {
        Optional<Room> optionalRoom = roomRepository.findById(newRoom.getId());
        if (optionalRoom.isEmpty()) {
            throw new IllegalStateException("Такого номера не существует");
        }
        Room room = optionalRoom.get();

        room.setName(newRoom.getName());
        room.setPrice(newRoom.getPrice());
        room.setUpdatedAt(LocalDate.now());

        return roomRepository.save(room);
    }
}
