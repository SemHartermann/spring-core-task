package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.entity.Trainer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.mapstruct.extensions.spring.DelegatingConverter;

@Mapper(componentModel = "spring")
public interface TrainerMapper extends Converter<Trainer, TrainerDto> {
    @Override
    TrainerDto convert(Trainer trainer);

    @InheritInverseConfiguration
    @DelegatingConverter
    Trainer invertConvert(TrainerDto trainerDto);
}