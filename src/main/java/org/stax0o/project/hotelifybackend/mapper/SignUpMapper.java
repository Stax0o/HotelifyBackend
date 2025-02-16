package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.stax0o.project.hotelifybackend.dto.SignUpDTO;
import org.stax0o.project.hotelifybackend.entity.User;

@Mapper(componentModel = "spring")
public interface SignUpMapper {
    User toEntity(SignUpDTO sign);
}
