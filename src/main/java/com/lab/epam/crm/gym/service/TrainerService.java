package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TrainerDto;

public interface TrainerService {
    TrainerDto createTrainer(TrainerDto trainerDto);

    TrainerDto getTrainerByUsername(String username);

    TrainerDto getTrainerById(Integer id);

    TrainerDto updateTrainerProfile(TrainerDto trainerDto);

    TrainerDto updateTrainerPassword(TrainerDto trainerDto, String newPassword);

    TrainerDto activateTrainer(TrainerDto trainerDto);

    TrainerDto deactivateTrainer(TrainerDto trainerDto);

    TrainerDto authenticate(String username, String password);
}