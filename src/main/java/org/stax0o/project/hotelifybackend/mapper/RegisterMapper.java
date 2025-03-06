package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.stax0o.project.hotelifybackend.dto.RegisterDTO;
import org.stax0o.project.hotelifybackend.entity.User;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    User toEntity(RegisterDTO sign);
}
