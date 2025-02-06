package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.dao.storage.TraineeStorage;
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
    private TraineeStorage inMemoryStorage;

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

        when(inMemoryStorage.getTrainees()).thenReturn(traineeMap);
    }

    @Test
    public void testSaveTrainee() {
        traineeDao.save(trainee);

        verify(inMemoryStorage, times(1)).getTrainees();
        assertEquals(trainee, inMemoryStorage.getTrainees().get(1));
    }

    @Test
    public void testFindById() {
        Trainee foundTrainee = traineeDao.findById(1);

        verify(inMemoryStorage, times(1)).getTrainees();
        assertEquals(trainee, foundTrainee);
    }

    @Test
    public void testFindAll() {
        List<Trainee> trainees = traineeDao.findAll();

        verify(inMemoryStorage, times(1)).getTrainees();
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

        verify(inMemoryStorage, atLeastOnce()).getTrainees();
        assertEquals(updatedTrainee, inMemoryStorage.getTrainees().get(1));
    }

    @Test
    public void testDeleteById() {
        traineeDao.deleteById(1);

        verify(inMemoryStorage, times(1)).getTrainees();
        assertEquals(0, inMemoryStorage.getTrainees().size());
    }
}