package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.repository.HotelRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel findById(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            throw new IllegalStateException("Такого отеля не существует");
        }
        return optionalHotel.get();
    }

    public List<Hotel> findByUserId(Long id) {
        return hotelRepository.findByUserId(id);
    }

    public Hotel update(Hotel newHotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(newHotel.getId());
        if (optionalHotel.isEmpty()) {
            throw new IllegalStateException("Такого отеля не существует");
        }
        Hotel hotel = optionalHotel.get();

        hotel.setName(newHotel.getName());
        hotel.setDescription(newHotel.getDescription());
        hotel.setCity(newHotel.getCity());
        hotel.setAddress(newHotel.getAddress());
        hotel.setPhone(newHotel.getPhone());
        hotel.setEmail(newHotel.getEmail());
        hotel.setUpdatedAt(LocalDate.now());

        return hotelRepository.save(hotel);
    }
}
