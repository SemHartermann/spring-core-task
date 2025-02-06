package com.lab.epam.crm.gym.dao.storage;

import com.lab.epam.crm.gym.entity.Training;
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
public class TrainingStorage {

    String dataFilePath;

    @Getter
    Map<Integer, Training> trainings;

    public TrainingStorage(@Value("${data.file.path}") String dataFilePath) {
        this.dataFilePath = dataFilePath;
        trainings = new HashMap<>();
    }

    public void init() {
        log.debug("Initializing TrainingStorage from file: {}", dataFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("training")) {
                    processTrainingLine(line.split(","));
                }
            }

            log.info("TrainingStorage initialized successfully");
        } catch (IOException e) {
            log.error("Failed to read data file: {}", e.getMessage(), e);
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
}