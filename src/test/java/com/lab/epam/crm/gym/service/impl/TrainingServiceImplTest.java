package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TrainingDao;
import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.entity.Training;
import com.lab.epam.crm.gym.mapper.TrainingMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest {
    @Mock
    private TrainingDao trainingDao;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    public void testCreateTraining() {
        TrainingDto trainingDto = new TrainingDto();
        when(trainingMapper.toEntity(any(TrainingDto.class))).thenReturn(new Training());

        trainingService.create(trainingDto);

        verify(trainingDao, times(1)).save(any(Training.class));
    }

    @Test
    public void testGetTrainingById() {
        Training training = new Training();
        when(trainingDao.findById(anyInt())).thenReturn(training);

        trainingService.getById(1);

        verify(trainingDao, times(1)).findById(1);
        verify(trainingMapper, times(1)).toDto(training);

        TrainingDto expected = new TrainingDto();
        when(trainingMapper.toDto(any())).thenReturn(expected);

        TrainingDto actual = trainingService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllTrainings() {
        List<Training> trainings = new ArrayList<>();
        when(trainingDao.findAll()).thenReturn(trainings);

        trainingService.getAll();

        verify(trainingDao, times(1)).findAll();
        verify(trainingMapper, times(1)).toDtoList(trainings);
    }
}