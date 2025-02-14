package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.mapper.HotelMapper;
import org.stax0o.project.hotelifybackend.repository.HotelRepository;
import org.stax0o.project.hotelifybackend.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final UserRepository userRepository;

    public HotelDTO create(HotelDTO hotelDTO) {
        return hotelMapper.toDTO(hotelRepository.save(hotelMapper.toEntity(hotelDTO)));
    }

    public HotelDTO findById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Такого отеля не существует"));
        return hotelMapper.toDTO(hotel);
    }

    public List<HotelDTO> findByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Такого пользователя не существует"));
        return hotelMapper.toDTOList(hotelRepository.findByUserId(userId));
    }

    public HotelDTO update(HotelDTO newHotelDTO) {
        Hotel hotel = hotelRepository.findById(newHotelDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Такого отеля не существует"));

        hotel.setName(newHotelDTO.name());
        hotel.setDescription(newHotelDTO.description());
        hotel.setCity(newHotelDTO.city());
        hotel.setAddress(newHotelDTO.address());
        hotel.setPhone(newHotelDTO.phone());
        hotel.setEmail(newHotelDTO.email());
        hotel.setUpdatedAt(LocalDate.now());

        return hotelMapper.toDTO(hotelRepository.save(hotel));
    }
}
