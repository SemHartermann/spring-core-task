package com.lab.epam.crm.gym.dao.impl;

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
    private Map<Integer, Training> storage;

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

        when(storage).thenReturn(trainingMap);
    }

    @Test
    public void testSaveTraining() {
        trainingDao.save(training);

        verify(storage, times(1));
        assertEquals(training, storage.get(1));
    }

    @Test
    public void testFindById() {
        Training foundTraining = trainingDao.findById(1);

        verify(storage, times(1));
        assertEquals(training, foundTraining);
    }

    @Test
    public void testFindAll() {
        List<Training> trainings = trainingDao.findAll();

        verify(storage, times(1));
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

        verify(storage, atLeastOnce());
        assertEquals(updatedTraining, storage.get(1));
    }

    @Test
    public void testDeleteById() {
        trainingDao.deleteById(1);

        verify(storage, times(1));
        assertEquals(0, storage.size());
    }
}