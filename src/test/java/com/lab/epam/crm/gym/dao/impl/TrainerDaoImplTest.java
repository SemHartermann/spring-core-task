package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.entity.Trainer;
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

public class TrainerDaoImplTest {

    @Mock
    private Map<Integer, Trainer> storage;

    @InjectMocks
    private TrainerDaoImpl trainerDao;

    private Trainer trainer;
    private Map<Integer, Trainer> trainerMap;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        trainer = new Trainer();
        trainer.setId(1);
        trainer.setFirstName("John");
        trainer.setLastName("Doe");

        trainerMap = new HashMap<>();
        trainerMap.put(1, trainer);

        when(storage).thenReturn(trainerMap);
    }

    @Test
    public void testSaveTrainer() {
        trainerDao.save(trainer);

        verify(storage, times(1));
        assertEquals(trainer, storage.get(1));
    }

    @Test
    public void testFindById() {
        Trainer foundTrainer = trainerDao.findById(1);

        verify(storage, times(1));
        assertEquals(trainer, foundTrainer);
    }

    @Test
    public void testFindAll() {
        List<Trainer> trainers = trainerDao.findAll();

        verify(storage, times(1));
        assertEquals(1, trainers.size());
        assertEquals(trainer, trainers.get(0));
    }

    @Test
    public void testUpdateTrainer() {
        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(1);
        updatedTrainer.setFirstName("Jane");
        updatedTrainer.setLastName("Doe");

        trainerDao.update(updatedTrainer);

        verify(storage, atLeastOnce());
        assertEquals(updatedTrainer, storage.get(1));
    }

    @Test
    public void testDeleteById() {
        trainerDao.deleteById(1);

        verify(storage, times(1));
        assertEquals(0, storage.size());
    }
}