package com.lab.epam.crm.gym.dao.storage;

import com.lab.epam.crm.gym.dao.storage.InMemoryStorage;
import com.lab.epam.crm.gym.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.test.util.ReflectionTestUtils;

public class InMemoryStorageTest {
    @InjectMocks
    private InMemoryStorage inMemoryStorage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitWithValidData() {
        ReflectionTestUtils.setField(inMemoryStorage, "dataFilePath", "src/test/resources/data/valid-data.csv");
        inMemoryStorage.init();

        Map<Integer, Trainer> trainers = inMemoryStorage.getTrainers();
        Map<Integer, Trainee> trainees = inMemoryStorage.getTrainees();
        Map<Integer, Training> trainings = inMemoryStorage.getTrainings();

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
        ReflectionTestUtils.setField(inMemoryStorage, "dataFilePath", "src/test/resources/data/invalid-data.csv");
        inMemoryStorage.init();

        Map<Integer, Trainer> trainers = inMemoryStorage.getTrainers();
        Map<Integer, Trainee> trainees = inMemoryStorage.getTrainees();
        Map<Integer, Training> trainings = inMemoryStorage.getTrainings();

        assertEquals(0, trainers.size());
        assertEquals(1, trainees.size());
        assertEquals(0, trainings.size());

        Trainee trainee = trainees.get(10);
        assertEquals("Jane", trainee.getFirstName());
        assertEquals("Smith", trainee.getLastName());
    }
}