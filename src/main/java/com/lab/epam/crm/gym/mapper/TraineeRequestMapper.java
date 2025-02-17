package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TraineeRequestDto;
import com.lab.epam.crm.gym.entity.Trainee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.mapstruct.extensions.spring.DelegatingConverter;

@Mapper(componentModel = "spring")
public interface TraineeRequestMapper extends Converter<Trainee, TraineeRequestDto> {
    @Override
    TraineeRequestDto convert(Trainee trainee);

    @InheritInverseConfiguration
    @DelegatingConverter
    Trainee invertConvert(TraineeRequestDto traineeRequestDto);
}