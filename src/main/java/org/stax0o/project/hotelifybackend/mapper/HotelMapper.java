package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Image;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "images", target = "imagePaths")
    HotelDTO toDTO(Hotel hotel);

    @Mapping(source = "userId", target = "user.id")
    Hotel toEntity(HotelDTO hotelDTO);

    List<HotelDTO> toDTOList(List<Hotel> hotels);

    List<Hotel> toEntityList(List<HotelDTO> hotelsDTOs);

    default List<String> mapImagesToPaths(List<Image> images) {
        return images.stream()
                .map(Image::getFilePath)
                .collect(Collectors.toList());
    }
}
