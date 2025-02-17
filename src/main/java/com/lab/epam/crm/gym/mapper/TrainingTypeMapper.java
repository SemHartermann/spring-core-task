package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TrainingTypeDto;
import com.lab.epam.crm.gym.entity.TrainingType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper extends Converter<TrainingType, TrainingTypeDto> {
    @Override
    TrainingTypeDto convert(TrainingType trainingType);

    @InheritInverseConfiguration
    @DelegatingConverter
    TrainingType invertConvert(TrainingTypeDto trainingTypeDto);
}