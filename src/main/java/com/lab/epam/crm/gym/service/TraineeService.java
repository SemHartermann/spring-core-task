package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TraineeDto;
import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.dto.TrainerDto;

import java.util.Date;
import java.util.List;

public interface TraineeService {
    TraineeDto createTrainee(TraineeDto traineeDto);

    TraineeDto getTraineeByUsername(String username);

    TraineeDto getTraineeById(Integer id);

    TraineeDto updateTraineeProfile(TraineeDto traineeDto);

    TraineeDto updateTraineePassword(TraineeDto traineeDto, String newPassword);

    TraineeDto activateTrainee(TraineeDto traineeDto);

    TraineeDto deactivateTrainee(TraineeDto traineeDto);

    void deleteTraineeProfileByUsername(String username);

    List<TrainingDto> getTraineeTrainings(String username, Date fromDate, Date toDate, String trainerName, String trainingType);

    List<TrainerDto> getUnassignedTrainers(String username);

    TraineeDto updateTraineeTrainersList(Integer traineeId, List<Integer> trainerIds);

    TraineeDto authenticate(String username, String password);
}