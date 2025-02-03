package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.UserDto;
import com.lab.epam.crm.gym.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);

    List<User> toEntityList(List<UserDto> userDtos);
}