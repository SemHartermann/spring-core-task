package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TrainingDto;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    TrainingDto createTraining(TrainingDto trainingDto);

    TrainingDto getTrainingById(Integer id);

    List<TrainingDto> getTraineeTrainings(String username, Date fromDate, Date toDate);

    List<TrainingDto> getTrainerTrainings(String username, Date fromDate, Date toDate);
}