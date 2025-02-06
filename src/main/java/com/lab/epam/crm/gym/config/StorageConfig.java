package com.lab.epam.crm.gym.config;

import com.lab.epam.crm.gym.entity.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@DependsOn({"storageLogger", "rootLogger"})
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties",
                ignoreResourceNotFound = true)})
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class StorageConfig {

    String dataFilePath;

    Map<Integer, Trainer> trainers;

    Map<Integer, Trainee> trainees;

    Map<Integer, Training> trainings;

    public StorageConfig(@Value("${data.file.path}") String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.trainers = new HashMap<>();
        this.trainees = new HashMap<>();
        this.trainings = new HashMap<>();
    }

    @Bean
    public Map<Integer, Trainer> trainerStorage() {
        return trainers;
    }

    @Bean
    public Map<Integer, Trainee> traineeStorage() {
        return trainees;
    }

    @Bean
    public Map<Integer, Training> trainingStorage() {
        return trainings;
    }

    public void init() {
        log.debug("Initializing InMemoryStorage from file: {}", dataFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                processLine(line);
            }

            log.info("InMemoryStorage initialized successfully");
        } catch (IOException e) {
            log.error("Failed to read data file: {}", e.getMessage(), e);
        }
    }

    private void processLine(String line) {
        log.trace("Processing line: {}", line);

        String[] values = line.split(",");

        switch (values[0]) {
            case "trainer" -> processTrainerLine(values);

            case "trainee" -> processTraineeLine(values);

            case "training" -> processTrainingLine(values);

            case null, default -> log.warn("Unknown record type: {}", values[0]);
        }
    }

    private void processTrainerLine(String[] values) {
        log.trace("Processing Trainer line and set fields");

        try {
            Trainer trainer = new Trainer();

            setUserFieldsFromLine(trainer, values);

            trainer.setSpecialization(TrainingType.valueOf(values[7]));

            trainers.put(trainer.getId(), trainer);

            log.debug("Loaded trainer: {}", trainer);
        } catch (Exception e) {
            log.error("Error processing trainer line: {}", values, e);
        }
    }

    private void processTraineeLine(String[] values) {
        log.trace("Processing Trainee line and set fields");

        try {
            Trainee trainee = new Trainee();

            setUserFieldsFromLine(trainee, values);

            trainee.setDateOfBirth(values[7]);
            trainee.setAddress(values[8]);

            trainees.put(trainee.getId(), trainee);

            log.debug("Loaded trainee: {}", trainee);
        } catch (Exception e) {
            log.error("Error processing trainee line: {}", values, e);
        }
    }

    private void processTrainingLine(String[] values) {
        log.trace("Processing Training line and set fields");

        try {
            Training training = new Training();
            training.setId(Integer.parseInt(values[1]));
            training.setTraineeId(Integer.parseInt(values[2]));
            training.setTrainerId(Integer.parseInt(values[3]));
            training.setName(values[4]);
            training.setType(TrainingType.valueOf(values[5]));
            training.setDate(values[6]);
            training.setDuration(Integer.parseInt(values[7]));

            trainings.put(training.getId(), training);

            log.debug("Loaded training: {}", training);
        } catch (Exception e) {
            log.error("Error processing training line: {}", values, e);
        }
    }

    private void setUserFieldsFromLine(User user, String[] values) {
        user.setId(Integer.parseInt(values[1]));
        user.setFirstName(values[2]);
        user.setLastName(values[3]);
        user.setUsername(values[4]);
        user.setPassword(values[5]);
        user.setActive(Boolean.parseBoolean(values[6]));
    }
}