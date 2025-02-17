package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.mapstruct.extensions.spring.DelegatingConverter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<User, UserRequestDto> {
    @Override
    UserRequestDto convert(User user);

    @InheritInverseConfiguration
    @DelegatingConverter
    User invertConvert(UserRequestDto userRequestDto);
}