package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.entity.Training;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.mapstruct.extensions.spring.DelegatingConverter;

@Mapper(componentModel = "spring")
public interface TrainingMapper extends Converter<Training, TrainingDto> {
    @Override
    TrainingDto convert(Training training);

    @InheritInverseConfiguration
    @DelegatingConverter
    Training invertConvert(TrainingDto trainingDto);
}