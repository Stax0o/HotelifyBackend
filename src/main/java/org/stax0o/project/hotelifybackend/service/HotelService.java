package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.dto.HotelsWithPriceDTO;
import org.stax0o.project.hotelifybackend.dto.RoomTypeDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.HotelMapper;
import org.stax0o.project.hotelifybackend.mapper.HotelsWithPriceMapper;
import org.stax0o.project.hotelifybackend.repository.BookingRepository;
import org.stax0o.project.hotelifybackend.repository.HotelRepository;
import org.stax0o.project.hotelifybackend.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final UserRepository userRepository;
    private final SaveImageService saveImageService;
    private final RoomService roomService;
    private final HotelsWithPriceMapper hotelsWithPriceMapper;
    private final BookingRepository bookingRepository;

    @Transactional
    public HotelDTO create(HotelDTO hotelDTO, List<MultipartFile> images, User user) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel.setUser(user);
        try {
            hotel.setImages(saveImageService.saveImage(images, hotel));
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка сохранения изображений");
        }

        return hotelMapper.toDTO(hotelRepository.save(hotel));
    }

    public HotelDTO findById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Такого отеля не существует"));
        bookingRepository.deleteOverdueUnpaidBookingsByHotel(id, LocalDate.now());
        return hotelMapper.toDTO(hotel);
    }

    public List<HotelDTO> findByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Такого пользователя не существует"));
        return hotelMapper.toDTOList(hotelRepository.findByUserId(userId));
    }

    @Transactional
    public HotelDTO update(HotelDTO newHotelDTO, List<MultipartFile> images, User user) {
        Hotel hotel = hotelRepository.findById(newHotelDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Такого отеля не существует"));
        if (!Objects.equals(hotel.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Отель не принадлежит данному пользователю");
        }

        hotel.setName(newHotelDTO.name());
        hotel.setDescription(newHotelDTO.description());
        hotel.setCity(newHotelDTO.city());
        hotel.setAddress(newHotelDTO.address());
        hotel.setPhone(newHotelDTO.phone());
        hotel.setEmail(newHotelDTO.email());
        hotel.setUpdatedAt(LocalDate.now());
        if (images != null && !images.isEmpty()) {
            try {
                hotel.getImages().clear();
                hotel.getImages().addAll(saveImageService.saveImage(images, hotel));
            } catch (IOException e) {
                throw new IllegalStateException("Ошибка сохранения изображений");
            }
        }

        return hotelMapper.toDTO(hotelRepository.save(hotel));
    }

    public List<HotelsWithPriceDTO> findAll() {
        LocalDate date = LocalDate.of(1970, 1, 1);

        return hotelRepository.findHotelsWithRoomsPricedAboveZero().stream()
                .map(hotel -> {
                    List<RoomTypeDTO> roomTypes = roomService.getAvailableRoomTypes(
                            hotel.getId(),
                            date,
                            date
                    );

                    Double minPrice = roomTypes.stream()
                            .mapToDouble(RoomTypeDTO::price)
                            .min()
                            .orElse(0.0);

                    return hotelsWithPriceMapper.toDTO(hotel, minPrice);
                })
                .toList();
    }
}
