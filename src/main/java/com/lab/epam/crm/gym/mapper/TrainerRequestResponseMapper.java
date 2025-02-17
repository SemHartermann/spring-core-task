package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TrainerRequestDto;
import com.lab.epam.crm.gym.dto.TrainerResponseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface TrainerRequestResponseMapper extends Converter<TrainerRequestDto, TrainerResponseDto> {
    @Override
    TrainerResponseDto convert(TrainerRequestDto trainerRequestDto);

    @InheritInverseConfiguration
    @DelegatingConverter
    TrainerRequestDto invertConvert(TrainerResponseDto trainerResponseDto);
}