package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.UserResponseDto;
import com.lab.epam.crm.gym.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends Converter<User, UserResponseDto> {
    @Override
    UserResponseDto convert(User user);

    @InheritInverseConfiguration
    @DelegatingConverter
    User invertConvert(UserResponseDto userResponseDto);
}