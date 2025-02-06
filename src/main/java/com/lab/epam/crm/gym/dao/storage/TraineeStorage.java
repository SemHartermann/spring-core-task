package com.lab.epam.crm.gym.dao.storage;

import com.lab.epam.crm.gym.entity.Trainee;
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
public class TraineeStorage {

    String dataFilePath;

    @Getter
    Map<Integer, Trainee> trainees;

    public TraineeStorage(@Value("${data.file.path}") String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.trainees = new HashMap<>();
    }

    public void init() {
        log.debug("Initializing TraineeStorage from file: {}", dataFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("trainee")) {
                    processTraineeLine(line.split(","));
                }
            }

            log.info("TraineeStorage initialized successfully");
        } catch (IOException e) {
            log.error("Failed to read data file: {}", e.getMessage(), e);
        }
    }

    private void processTraineeLine(String[] values) {
        log.trace("Processing Trainee line and set fields");

        try {
            Trainee trainee = new Trainee();
            trainee.setId(Integer.parseInt(values[1]));
            trainee.setFirstName(values[2]);
            trainee.setLastName(values[3]);
            trainee.setUsername(values[4]);
            trainee.setPassword(values[5]);
            trainee.setActive(Boolean.parseBoolean(values[6]));
            trainee.setDateOfBirth(values[7]);
            trainee.setAddress(values[8]);
            trainees.put(trainee.getId(), trainee);
            log.debug("Loaded trainee: {}", trainee);
        } catch (Exception e) {
            log.error("Error processing trainee line: {}", values, e);
        }
    }
}