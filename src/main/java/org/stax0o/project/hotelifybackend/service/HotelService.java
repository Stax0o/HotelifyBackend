package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.mapper.HotelMapper;
import org.stax0o.project.hotelifybackend.repository.HotelRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelDTO create(HotelDTO hotelDTO) {
        return hotelMapper.toDTO(hotelRepository.save(hotelMapper.toEntity(hotelDTO)));
    }

    public HotelDTO findById(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            throw new IllegalStateException("Такого отеля не существует");
        }
        return hotelMapper.toDTO(optionalHotel.get());
    }

    public List<HotelDTO> findByUserId(Long id) {
        return hotelMapper.toDTOList(hotelRepository.findByUserId(id));
    }

    public HotelDTO update(HotelDTO newHotelDTO) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(newHotelDTO.id());
        if (optionalHotel.isEmpty()) {
            throw new IllegalStateException("Такого отеля не существует");
        }
        Hotel hotel = optionalHotel.get();

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
