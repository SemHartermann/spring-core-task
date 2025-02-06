package com.lab.epam.crm.gym.dao.storage;

import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.TrainingType;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Component
@DependsOn({"storageLogger", "rootLogger"})
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class TrainerStorage {

    String dataFilePath;

    @Getter
    Map<Integer, Trainer> trainers;

    public TrainerStorage(@Value("${data.file.path}") String dataFilePath) {
        this.dataFilePath = dataFilePath;
        trainers = new HashMap<>();
    }

    public void init() {
        log.debug("Initializing TrainerStorage from file: {}", dataFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("trainer")) {
                    processTrainerLine(line.split(","));
                }
            }

            log.info("TrainerStorage initialized successfully");
        } catch (IOException e) {
            log.error("Failed to read data file: {}", e.getMessage(), e);
        }
    }

    private void processTrainerLine(String[] values) {
        log.trace("Processing Trainer line and set fields");

        try {
            Trainer trainer = new Trainer();
            trainer.setId(Integer.parseInt(values[1]));
            trainer.setFirstName(values[2]);
            trainer.setLastName(values[3]);
            trainer.setUsername(values[4]);
            trainer.setPassword(values[5]);
            trainer.setActive(Boolean.parseBoolean(values[6]));
            trainer.setSpecialization(TrainingType.valueOf(values[7]));
            trainers.put(trainer.getId(), trainer);
            log.debug("Loaded trainer: {}", trainer);
        } catch (Exception e) {
            log.error("Error processing trainer line: {}", values, e);
        }
    }
}