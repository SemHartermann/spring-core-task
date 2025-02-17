package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TraineeRequestDto;
import com.lab.epam.crm.gym.dto.TraineeResponseDto;
import com.lab.epam.crm.gym.dto.TrainerResponseDto;
import com.lab.epam.crm.gym.dto.TrainingDto;

import java.util.Date;
import java.util.List;

public interface TraineeService {
    TraineeResponseDto createTrainee(TraineeRequestDto traineeRequestDto);

    TraineeResponseDto getTraineeByUsername(String username);

    TraineeResponseDto getTraineeById(Integer id);

    TraineeResponseDto updateTraineeProfile(TraineeRequestDto traineeRequestDto);

    TraineeResponseDto updateTraineePassword(TraineeRequestDto traineeRequestDto, String newPassword);

    TraineeResponseDto activateTrainee(TraineeRequestDto traineeRequestDto);

    TraineeResponseDto deactivateTrainee(TraineeRequestDto traineeRequestDto);

    void deleteTraineeProfileByUsername(String username);

    List<TrainingDto> getTraineeTrainings(String username, Date fromDate, Date toDate, String trainerName, String trainingType);

    List<TrainerResponseDto> getUnassignedTrainers(String username);

    TraineeResponseDto updateTraineeTrainersList(Integer traineeId, List<Integer> trainerIds);

    TraineeResponseDto authenticate(String username, String password);
}