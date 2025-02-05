package com.lab.epam.crm.gym.dao;

import com.lab.epam.crm.gym.entity.Training;

import java.util.List;

public interface TrainingDao {
    void save(Training training);

    Training findById(int id);

    List<Training> findAll();

    void update(Training training);

    void deleteById(int id);
}