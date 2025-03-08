package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.dto.RoomDTO;
import org.stax0o.project.hotelifybackend.dto.RoomTypeDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Room;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.RoomMapper;
import org.stax0o.project.hotelifybackend.repository.BookingRepository;
import org.stax0o.project.hotelifybackend.repository.HotelRepository;
import org.stax0o.project.hotelifybackend.repository.RoomRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    public List<RoomDTO> create(RoomDTO roomDTO, User user, Integer count) {
        Hotel hotel = hotelRepository.findById(roomDTO.hotelId())
                .orElseThrow(() -> new IllegalArgumentException("Такого отеля не существует"));
        if (!Objects.equals(hotel.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Данный отель не принадлежит этому пользователю");
        }
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Room room = roomMapper.toEntity(roomDTO);
            room.setHotel(hotel);
            roomDTOList.add(roomMapper.toDTO(roomRepository.save(room)));
        }
        return roomDTOList;
    }

    public RoomDTO findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Номера с таким id не существует"));
        return roomMapper.toDTO(room);
    }

    public List<RoomTypeDTO> findByHotelId(Long hotelId, User user) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalStateException("Отеля с таким id не существует"));
        if (!Objects.equals(hotel.getUser().getId(), user.getId())) {
            throw new IllegalStateException("Отель не принадлежит этому пользователю");
        }

        List<Room> rooms = roomRepository.findByHotelIdAndIsDeleteFalse(hotelId);
        return rooms.stream()
                .collect(Collectors.groupingBy(
                        Room::getName,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    Room anyRoom = list.getFirst();
                                    return new RoomTypeDTO(anyRoom.getId(), anyRoom.getName(), anyRoom.getPrice(), list.size());
                                }
                        )
                ))
                .values()
                .stream()
                .toList();
    }


    public List<RoomTypeDTO> getAvailableRoomTypes(Long hotelId,
                                                   LocalDate startDate,
                                                   LocalDate endDate) {

        List<Room> rooms = roomRepository.findAvailableRooms(hotelId, startDate, endDate, LocalDate.now());

        return rooms.stream()
                .map(room -> new RoomTypeDTO(room.getId(), room.getName(), room.getPrice(), 0))
                .collect(Collectors.toMap(
                        RoomTypeDTO::name,
                        dto -> dto,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();
    }

    @Transactional
    public void update(User user, RoomTypeDTO newRoomTypeDTO) {
        List<Room> rooms = roomRepository.findByNameAndIsDeleteFalse(newRoomTypeDTO.name());

        if (rooms.isEmpty()) {
            throw new IllegalArgumentException("Комнат такого типа нет");
        }

        if (!Objects.equals(rooms.getFirst().getHotel().getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Комнаты не принадлежат данному пользователю");
        }

        int currentCount = rooms.size() - newRoomTypeDTO.count();


        if (currentCount <= 0) {
            List<Room> newRooms = new ArrayList<>();
            for (int i = 0; i < Math.abs(currentCount); i++) {
                Room newRoom = new Room();
                newRoom.setName(newRoomTypeDTO.name());
                newRoom.setHotel(rooms.getFirst().getHotel());
                newRoom.setPrice(newRoomTypeDTO.price());

                newRooms.add(newRoom);
            }
            roomRepository.saveAll(newRooms);

            for (Room room : rooms) {
                room.setName(newRoomTypeDTO.name());
                room.setPrice(newRoomTypeDTO.price());
            }
        } else {
            for (int i = 0; i < currentCount; i++) {
                rooms.get(i).setIsDelete(true);
            }

            List<Room> newRooms = roomRepository.findByNameAndIsDeleteFalse(newRoomTypeDTO.name());
            for (Room room : newRooms) {
                room.setName(newRoomTypeDTO.name());
                room.setPrice(newRoomTypeDTO.price());
            }
        }
    }
}
