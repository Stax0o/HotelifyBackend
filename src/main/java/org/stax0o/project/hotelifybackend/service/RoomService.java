package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.dto.RoomDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.mapper.RoomMapper;
import org.stax0o.project.hotelifybackend.repository.HotelRepository;
import org.stax0o.project.hotelifybackend.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;

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
