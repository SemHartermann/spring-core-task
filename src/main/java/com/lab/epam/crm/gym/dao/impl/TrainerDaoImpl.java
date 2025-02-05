package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.dao.TrainerDao;
import com.lab.epam.crm.gym.dao.storage.InMemoryStorage;
import com.lab.epam.crm.gym.entity.Trainer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@DependsOn({"daoLogger", "rootLogger"})
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TrainerDaoImpl implements TrainerDao {

    InMemoryStorage storage;

    @Override
    public void save(Trainer trainer) {
        log.debug("Saving trainer: {}", trainer);

        storage.getTrainers().put(trainer.getId(), trainer);

        log.info("Trainer saved with ID: {}", trainer.getId());
    }

    @Override
    public Trainer findById(int id) {
        log.debug("Finding trainer by ID: {}", id);

        Trainer trainer = storage.getTrainers().get(id);

        if (trainer == null) {
            log.warn("Trainer with ID {} not found", id);
        } else {
            log.info("Trainer found with ID: {}", id);
        }

        return trainer;
    }

    @Override
    public List<Trainer> findAll() {
        log.debug("Finding all trainers");

        List<Trainer> trainers = new ArrayList<>(storage.getTrainers().values());

        log.info("Number of trainers found: {}", trainers.size());

        return trainers;
    }

    @Override
    public void update(Trainer trainer) {
        log.debug("Updating trainer: {}", trainer);

        if (storage.getTrainers().containsKey(trainer.getId())) {
            storage.getTrainers().put(trainer.getId(), trainer);

            log.info("Trainer updated with ID: {}", trainer.getId());
        } else {
            log.warn("Trainer with ID {} not found for update", trainer.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        log.debug("Deleting trainer by ID: {}", id);

        if (storage.getTrainers().remove(id) != null) {
            log.info("Trainer deleted with ID: {}", id);
        } else {
            log.warn("Trainer with ID {} not found for deletion", id);
        }
    }
}