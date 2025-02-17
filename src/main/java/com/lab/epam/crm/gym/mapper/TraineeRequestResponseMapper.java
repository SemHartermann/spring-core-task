package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TraineeRequestDto;
import com.lab.epam.crm.gym.dto.TraineeResponseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface TraineeRequestResponseMapper extends Converter<TraineeRequestDto, TraineeResponseDto> {
    @Override
    TraineeResponseDto convert(TraineeRequestDto traineeRequestDto);

    @InheritInverseConfiguration
    @DelegatingConverter
    TraineeRequestDto invertConvert(TraineeResponseDto traineeResponseDto);
}