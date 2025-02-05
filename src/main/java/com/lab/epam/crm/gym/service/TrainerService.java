package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TrainerDto;

import java.util.List;

public interface TrainerService {
    void create(TrainerDto trainer);

    void update(TrainerDto trainer);

    TrainerDto getById(Integer id);

    List<TrainerDto> getAll();
}