package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.dao.TrainingDao;
import com.lab.epam.crm.gym.entity.Training;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@DependsOn({"daoLogger", "rootLogger"})
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TrainingDaoImpl implements TrainingDao {

    Map<Integer, Training> storage;

    @Override
    public void save(Training training) {
        log.debug("Saving training: {}", training);

        storage.put(training.getId(), training);

        log.info("Training saved with ID: {}", training.getId());
    }

    @Override
    public Training findById(int id) {
        log.debug("Finding training by ID: {}", id);

        Training training = storage.get(id);

        if (training == null) {
            log.warn("Training with ID {} not found", id);
        } else {
            log.info("Training found with ID: {}", id);
        }

        return training;
    }

    @Override
    public List<Training> findAll() {
        log.debug("Finding all trainings");

        List<Training> trainings = new ArrayList<>(storage.values());

        log.info("Number of trainings found: {}", trainings.size());

        return trainings;
    }

    @Override
    public void update(Training training) {
        log.debug("Updating training: {}", training);

        if (storage.containsKey(training.getId())) {
            storage.put(training.getId(), training);

            log.info("Training updated with ID: {}", training.getId());
        } else {
            log.warn("Training with ID {} not found for update", training.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        log.debug("Deleting training by ID: {}", id);

        if (storage.remove(id) != null) {
            log.info("Training deleted with ID: {}", id);
        } else {
            log.warn("Training with ID {} not found for deletion", id);
        }
    }
}