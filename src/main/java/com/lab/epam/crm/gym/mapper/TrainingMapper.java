package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.entity.Training;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    TrainingDto toDto(Training training);

    Training toEntity(TrainingDto trainingDto);

    List<TrainingDto> toDtoList(List<Training> trainings);

    List<Training> toEntityList(List<TrainingDto> trainingDtos);
}