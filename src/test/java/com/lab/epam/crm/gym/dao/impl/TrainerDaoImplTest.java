package com.lab.epam.crm.gym.dao.impl;

import com.lab.epam.crm.gym.dao.storage.InMemoryStorage;
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
    private InMemoryStorage inMemoryStorage;

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

        when(inMemoryStorage.getTrainers()).thenReturn(trainerMap);
    }

    @Test
    public void testSaveTrainer() {
        trainerDao.save(trainer);

        verify(inMemoryStorage, times(1)).getTrainers();
        assertEquals(trainer, inMemoryStorage.getTrainers().get(1));
    }

    @Test
    public void testFindById() {
        Trainer foundTrainer = trainerDao.findById(1);

        verify(inMemoryStorage, times(1)).getTrainers();
        assertEquals(trainer, foundTrainer);
    }

    @Test
    public void testFindAll() {
        List<Trainer> trainers = trainerDao.findAll();

        verify(inMemoryStorage, times(1)).getTrainers();
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

        // Проверка вызова метода getTrainers без times(), так как он вызывается дважды
        verify(inMemoryStorage, atLeastOnce()).getTrainers();
        assertEquals(updatedTrainer, inMemoryStorage.getTrainers().get(1));
    }

    @Test
    public void testDeleteById() {
        trainerDao.deleteById(1);

        verify(inMemoryStorage, times(1)).getTrainers();
        assertEquals(0, inMemoryStorage.getTrainers().size());
    }
}