package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.entity.Trainee;
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

public class TraineeDaoImplTest {

    @Mock
    private Map<Integer, Trainee> storage;

    @InjectMocks
    private TraineeDaoImpl traineeDao;

    private Trainee trainee;
    private Map<Integer, Trainee> traineeMap;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        trainee = new Trainee();
        trainee.setId(1);
        trainee.setFirstName("Jane");
        trainee.setLastName("Doe");

        traineeMap = new HashMap<>();
        traineeMap.put(1, trainee);

        storage = traineeMap;
    }

    @Test
    public void testSaveTrainee() {
        traineeDao.save(trainee);

        verify(storage, times(1));
        assertEquals(trainee, storage.get(1));
    }

    @Test
    public void testFindById() {
        Trainee foundTrainee = traineeDao.findById(1);

        verify(storage, times(1));
        assertEquals(trainee, foundTrainee);
    }

    @Test
    public void testFindAll() {
        List<Trainee> trainees = traineeDao.findAll();

        verify(storage, times(1));
        assertEquals(1, trainees.size());
        assertEquals(trainee, trainees.get(0));
    }

    @Test
    public void testUpdateTrainee() {
        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(1);
        updatedTrainee.setFirstName("Jane");
        updatedTrainee.setLastName("Smith");

        traineeDao.update(updatedTrainee);

        verify(storage, atLeastOnce());
        assertEquals(updatedTrainee, storage.get(1));
    }

    @Test
    public void testDeleteById() {
        traineeDao.deleteById(1);

        verify(storage, times(1));
        assertEquals(0, storage.size());
    }
}