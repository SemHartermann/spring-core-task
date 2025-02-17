package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TrainerRequestDto;
import com.lab.epam.crm.gym.dto.TrainerResponseDto;

public interface TrainerService {
    TrainerResponseDto createTrainer(TrainerRequestDto trainerRequestDto);

    TrainerResponseDto getTrainerByUsername(String username);

    TrainerResponseDto getTrainerById(Integer id);

    TrainerResponseDto updateTrainerProfile(TrainerRequestDto trainerRequestDto);

    TrainerResponseDto updateTrainerPassword(TrainerRequestDto trainerRequestDto, String newPassword);

    TrainerResponseDto activateTrainer(TrainerRequestDto trainerRequestDto);

    TrainerResponseDto deactivateTrainer(TrainerRequestDto trainerRequestDto);

    TrainerResponseDto authenticate(String username, String password);
}