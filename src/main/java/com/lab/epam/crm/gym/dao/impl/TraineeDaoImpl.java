package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.dao.TraineeDao;
import com.lab.epam.crm.gym.dao.storage.InMemoryStorage;
import com.lab.epam.crm.gym.entity.Trainee;
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
public class TraineeDaoImpl implements TraineeDao {

    InMemoryStorage storage;

    @Override
    public void save(Trainee trainee) {
        log.debug("Saving trainee: {}", trainee);

        storage.getTrainees().put(trainee.getId(), trainee);

        log.info("Trainee saved with ID: {}", trainee.getId());
    }

    @Override
    public Trainee findById(int id) {
        log.debug("Finding trainee by ID: {}", id);

        Trainee trainee = storage.getTrainees().get(id);

        if (trainee == null) {
            log.warn("Trainee with ID {} not found", id);
        } else {
            log.info("Trainee found with ID: {}", id);
        }
        return trainee;
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Finding all trainees");

        List<Trainee> trainees = new ArrayList<>(storage.getTrainees().values());

        log.info("Number of trainees found: {}", trainees.size());

        return trainees;
    }

    @Override
    public void update(Trainee trainee) {
        log.debug("Updating trainee: {}", trainee);

        if (storage.getTrainees().containsKey(trainee.getId())) {
            storage.getTrainees().put(trainee.getId(), trainee);

            log.info("Trainee updated with ID: {}", trainee.getId());
        } else {
            log.warn("Trainee with ID {} not found for update", trainee.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        log.debug("Deleting trainee by ID: {}", id);

        if (storage.getTrainees().remove(id) != null) {
            log.info("Trainee deleted with ID: {}", id);
        } else {
            log.warn("Trainee with ID {} not found for deletion", id);
        }
    }
}