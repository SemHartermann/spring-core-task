package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.dao.storage.InMemoryStorage;
import com.lab.epam.crm.gym.entity.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainingDaoImplTest {

    @Mock
    private InMemoryStorage inMemoryStorage;

    @InjectMocks
    private TrainingDaoImpl trainingDao;

    private Training training;
    private Map<Integer, Training> trainingMap;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        training = new Training();
        training.setId(1);
        training.setTraineeId(1);
        training.setTrainerId(1);
        training.setName("Morning Cardio Session");

        trainingMap = new HashMap<>();
        trainingMap.put(1, training);

        when(inMemoryStorage.getTrainings()).thenReturn(trainingMap);
    }

    @Test
    public void testSaveTraining() {
        trainingDao.save(training);

        verify(inMemoryStorage, times(1)).getTrainings();
        assertEquals(training, inMemoryStorage.getTrainings().get(1));
    }

    @Test
    public void testFindById() {
        Training foundTraining = trainingDao.findById(1);

        verify(inMemoryStorage, times(1)).getTrainings();
        assertEquals(training, foundTraining);
    }

    @Test
    public void testFindAll() {
        List<Training> trainings = trainingDao.findAll();

        verify(inMemoryStorage, times(1)).getTrainings();
        assertEquals(1, trainings.size());
        assertEquals(training, trainings.get(0));
    }

    @Test
    public void testUpdateTraining() {
        Training updatedTraining = new Training();
        updatedTraining.setId(1);
        updatedTraining.setTraineeId(2);
        updatedTraining.setTrainerId(2);
        updatedTraining.setName("Evening Cardio Session");

        trainingDao.update(updatedTraining);

        verify(inMemoryStorage, atLeastOnce()).getTrainings();
        assertEquals(updatedTraining, inMemoryStorage.getTrainings().get(1));
    }

    @Test
    public void testDeleteById() {
        trainingDao.deleteById(1);

        verify(inMemoryStorage, times(1)).getTrainings();
        assertEquals(0, inMemoryStorage.getTrainings().size());
    }
}