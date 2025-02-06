package com.lab.epam.crm.gym.dao.storage;

import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryStorageTest {
    @InjectMocks
    private TraineeStorage traineeStorage;

    @InjectMocks
    private TrainerStorage trainerStorage;

    @InjectMocks
    private TrainingStorage trainingStorage;



    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitWithValidData() {
        ReflectionTestUtils.setField(traineeStorage, "dataFilePath", "src/test/resources/data/valid-data.csv");
        ReflectionTestUtils.setField(trainerStorage, "dataFilePath", "src/test/resources/data/valid-data.csv");
        ReflectionTestUtils.setField(trainingStorage, "dataFilePath", "src/test/resources/data/valid-data.csv");
        traineeStorage.init();
        trainerStorage.init();
        trainingStorage.init();

        Map<Integer, Trainer> trainers = trainerStorage.getTrainers();
        Map<Integer, Trainee> trainees = traineeStorage.getTrainees();
        Map<Integer, Training> trainings = trainingStorage.getTrainings();

        assertEquals(1, trainers.size());
        assertEquals(1, trainees.size());
        assertEquals(1, trainings.size());

        Trainer trainer = trainers.get(1);
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());

        Trainee trainee = trainees.get(2);
        assertEquals("Jane", trainee.getFirstName());
        assertEquals("Smith", trainee.getLastName());

        Training training = trainings.get(1);
        assertEquals("Morning Cardio Session", training.getName());
    }

    @Test
    public void testInitWithInvalidData() {
        ReflectionTestUtils.setField(traineeStorage, "dataFilePath", "src/test/resources/data/invalid-data.csv");
        ReflectionTestUtils.setField(trainerStorage, "dataFilePath", "src/test/resources/data/invalid-data.csv");
        ReflectionTestUtils.setField(trainingStorage, "dataFilePath", "src/test/resources/data/invalid-data.csv");
        traineeStorage.init();
        trainerStorage.init();
        trainingStorage.init();

        Map<Integer, Trainer> trainers = trainerStorage.getTrainers();
        Map<Integer, Trainee> trainees = traineeStorage.getTrainees();
        Map<Integer, Training> trainings = trainingStorage.getTrainings();

        assertEquals(0, trainers.size());
        assertEquals(1, trainees.size());
        assertEquals(0, trainings.size());

        Trainee trainee = trainees.get(10);
        assertEquals("Jane", trainee.getFirstName());
        assertEquals("Smith", trainee.getLastName());
    }
}