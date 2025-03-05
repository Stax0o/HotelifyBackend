package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.stax0o.project.hotelifybackend.dto.RoomDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.mapper.RoomMapper;
import org.stax0o.project.hotelifybackend.repository.HotelRepository;
import org.stax0o.project.hotelifybackend.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    public RoomDTO create(RoomDTO roomDTO) {
        Hotel hotel = hotelRepository.findById(roomDTO.hotelId())
                .orElseThrow(() -> new IllegalArgumentException("Такого отеля не существует"));
        Room room = roomMapper.toEntity(roomDTO);
        room.setHotel(hotel);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    public RoomDTO findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Номера с таким id не существует"));
        return roomMapper.toDTO(room);
    }

    public List<RoomDTO> findByHotelId(Long hotelId) {
        hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalStateException("Отеля с таким id не существует"));

        return roomMapper.toDTOList(roomRepository.findByHotelId(hotelId));
    }

    public List<String> getAvailableRoomTypes(Long hotelId,
                                              LocalDate startDate,
                                              LocalDate endDate) {
        List<Room> rooms = roomRepository.findAvailableRooms(hotelId, startDate, endDate);
        return rooms.stream()
                .map(Room::getName)
                .collect(Collectors.toSet())
                .stream()
                .toList();
    }

    @Transactional
    public RoomDTO update(RoomDTO newRoomDTO) {
        Room room = roomRepository.findById(newRoomDTO.id())
                .orElseThrow(() -> new IllegalStateException("Такого номера не существует"));

        room.setName(newRoomDTO.name());
        room.setPrice(newRoomDTO.price());
        room.setUpdatedAt(LocalDate.now());

        return roomMapper.toDTO(roomRepository.save(room));
    }
}
