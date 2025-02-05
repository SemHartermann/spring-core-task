package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TrainerDao;
import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.mapper.TrainerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceImplTest {
    @Mock
    private TrainerDao trainerDao;

    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    public void setup() {
        Mockito.lenient().when(userProfileService.generateUsername(any())).thenReturn("john.doe1");
        Mockito.lenient().when(userProfileService.generateRandomPassword()).thenReturn("password");
    }

    @Test
    public void testCreateTrainer() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("John");
        trainerDto.setLastName("Doe");

        Trainer trainer = new Trainer();
        when(trainerMapper.toEntity(any(TrainerDto.class))).thenReturn(trainer);

        trainerService.create(trainerDto);

        verify(trainerDao, times(1)).save(any(Trainer.class));
    }

    @Test
    public void testGetTrainerById() {
        Trainer trainer = new Trainer();
        when(trainerDao.findById(anyInt())).thenReturn(trainer);

        trainerService.getById(1);

        verify(trainerDao, times(1)).findById(1);
        verify(trainerMapper, times(1)).toDto(trainer);

        TrainerDto expected = new TrainerDto();
        when(trainerMapper.toDto(any())).thenReturn(expected);

        TrainerDto actual = trainerService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateTrainer() {
        TrainerDto trainerDto = new TrainerDto();
        Trainer trainer = new Trainer();
        when(trainerMapper.toEntity(any(TrainerDto.class))).thenReturn(trainer);

        trainerService.update(trainerDto);

        verify(trainerDao, times(1)).update(trainer);
    }

    @Test
    public void testGetAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        when(trainerDao.findAll()).thenReturn(trainers);

        trainerService.getAll();

        verify(trainerDao, times(1)).findAll();
        verify(trainerMapper, times(1)).toDtoList(trainers);
    }
}