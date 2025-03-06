package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.repository.query.Param;
import org.stax0o.project.hotelifybackend.dto.HotelsWithPriceDTO;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Image;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelsWithPriceMapper {

    @Mapping(target = "imagePaths", expression = "java(mapImagesToPaths(hotel.getImages()))")
    @Mapping(target = "minPrice", source = "price")
    HotelsWithPriceDTO toDTO(Hotel hotel, @Param("price") Double price);

    List<HotelsWithPriceDTO> toDTOList(List<Hotel> hotels);

    @Named("mapImagesToPaths")
    default List<String> mapImagesToPaths(List<Image> images) {
        return images == null ? List.of() : images.stream()
                .map(Image::getFilePath)
                .toList();
    }
}

