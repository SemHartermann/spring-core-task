package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.entity.Trainer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    TrainerDto toDto(Trainer trainer);

    Trainer toEntity(TrainerDto trainerDto);

    List<TrainerDto> toDtoList(List<Trainer> trainers);

    List<Trainer> toEntityList(List<TrainerDto> trainerDtos);
}