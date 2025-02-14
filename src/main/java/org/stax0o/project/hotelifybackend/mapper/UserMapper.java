package org.stax0o.project.hotelifybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.stax0o.project.hotelifybackend.dto.UserDTO;
import org.stax0o.project.hotelifybackend.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    @Mapping(target = "balance", ignore = true)
    User toEntity(UserDTO userDTO);

    List<UserDTO> toDTOList(List<User> users);

    List<User> toEntityList(List<UserDTO> userDTOs);
}
