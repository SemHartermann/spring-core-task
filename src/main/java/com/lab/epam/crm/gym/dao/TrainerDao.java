package com.lab.epam.crm.gym.dao;

import com.lab.epam.crm.gym.entity.Trainer;

import java.util.List;

public interface TrainerDao {
    void save(Trainer trainer);

    Trainer findById(int id);

    List<Trainer> findAll();

    void update(Trainer trainer);

    void deleteById(int id);
}