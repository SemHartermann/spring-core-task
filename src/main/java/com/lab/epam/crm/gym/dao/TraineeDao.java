package com.lab.epam.crm.gym.dao;

import com.lab.epam.crm.gym.entity.Trainee;

import java.util.List;

public interface TraineeDao {
    void save(Trainee trainee);

    Trainee findById(int id);

    List<Trainee> findAll();

    void update(Trainee trainee);

    void deleteById(int id);
}