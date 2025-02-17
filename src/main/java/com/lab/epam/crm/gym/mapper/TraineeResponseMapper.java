package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TraineeResponseDto;
import com.lab.epam.crm.gym.entity.Trainee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface TraineeResponseMapper extends Converter<Trainee, TraineeResponseDto> {
    @Override
    TraineeResponseDto convert(Trainee trainee);

    @InheritInverseConfiguration
    @DelegatingConverter
    Trainee invertConvert(TraineeResponseDto traineeResponseDto);
}