package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.UserDto;
import com.lab.epam.crm.gym.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.mapstruct.extensions.spring.DelegatingConverter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<User, UserDto> {
    @Override
    UserDto convert(User user);

    @InheritInverseConfiguration
    @DelegatingConverter
    User invertConvert(UserDto userDto);
}