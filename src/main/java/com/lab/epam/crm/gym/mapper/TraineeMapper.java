package com.lab.epam.crm.gym.mapper;

import com.lab.epam.crm.gym.dto.TraineeDto;
import com.lab.epam.crm.gym.entity.Trainee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    TraineeDto toDto(Trainee trainee);

    Trainee toEntity(TraineeDto traineeDto);

    List<TraineeDto> toDtoList(List<Trainee> trainees);

    List<Trainee> toEntityList(List<TraineeDto> traineeDtos);
}