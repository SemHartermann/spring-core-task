package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.TraineeDto;

import java.util.List;

public interface TraineeService {
    void create(TraineeDto trainee);

    void update(TraineeDto trainee);

    void deleteById(Integer id);

    TraineeDto getById(Integer id);

    List<TraineeDto> getAll();
}