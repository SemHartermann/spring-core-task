package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.dto.UserResponseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserRequestResponseMapper extends Converter<UserRequestDto, UserResponseDto> {
    @Override
    UserResponseDto convert(UserRequestDto userRequestDto);

    @InheritInverseConfiguration
    @DelegatingConverter
    UserRequestDto invertConvert(UserResponseDto userResponseDto);
}