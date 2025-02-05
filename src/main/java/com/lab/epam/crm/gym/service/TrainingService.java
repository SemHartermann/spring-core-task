package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TrainingDto;

import java.util.List;

public interface TrainingService {
    void create(TrainingDto training);

    TrainingDto getById(Integer id);

    List<TrainingDto> getAll();
}