package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.stax0o.project.hotelifybackend.dto.HotelDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Image;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "images", target = "imagePaths", qualifiedByName = "mapImagesToPaths")
    HotelDTO toDTO(Hotel hotel);

    @Mapping(source = "userId", target = "user.id")
    Hotel toEntity(HotelDTO hotelDTO);

    List<HotelDTO> toDTOList(List<Hotel> hotels);

    List<Hotel> toEntityList(List<HotelDTO> hotelsDTOs);

    @Named("mapImagesToPaths")
    default List<String> mapImagesToPaths(List<Image> images) {
        return images == null ? List.of() : images.stream()
                .map(Image::getFilePath)
                .toList();
    }
}
