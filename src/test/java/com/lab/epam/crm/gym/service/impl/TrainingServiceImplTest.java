package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.entity.Training;
import com.lab.epam.crm.gym.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private ConversionService conversionService;

    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingService = new TrainingServiceImpl(trainingRepository, conversionService);
    }

    @Test
    void createTraining_Success() {
        TrainingDto trainingDto = new TrainingDto();
        Training training = new Training();
        Training savedTraining = new Training();

        when(conversionService.convert(trainingDto, Training.class)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(savedTraining);
        when(conversionService.convert(savedTraining, TrainingDto.class)).thenReturn(trainingDto);

        TrainingDto result = trainingService.createTraining(trainingDto);

        assertNotNull(result);
        verify(trainingRepository, times(1)).save(training);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void getTrainingById_Success() {
        Integer id = 1;
        Training training = new Training();
        TrainingDto trainingDto = new TrainingDto();

        when(trainingRepository.findById(id)).thenReturn(Optional.of(training));
        when(conversionService.convert(training, TrainingDto.class)).thenReturn(trainingDto);

        TrainingDto result = trainingService.getTrainingById(id);

        assertNotNull(result);
        verify(trainingRepository, times(1)).findById(id);
        verify(conversionService, times(1)).convert(training, TrainingDto.class);
    }

    @Test
    void getTraineeTrainings_Success() {
        String username = "testUser";
        Date fromDate = new Date();
        Date toDate = new Date();
        List<TrainingDto> trainingDtos = List.of(new TrainingDto());

        when(trainingRepository.findAllByTraineeUserUsernameAndTrainingDateBetween(username, fromDate, toDate))
                .thenReturn(List.of(new Training()));
        when(conversionService.convert(any(Training.class), eq(TrainingDto.class))).thenReturn(new TrainingDto());

        List<TrainingDto> result = trainingService.getTraineeTrainings(username, fromDate, toDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingRepository, times(1)).findAllByTraineeUserUsernameAndTrainingDateBetween(username, fromDate, toDate);
        verify(conversionService, times(1)).convert(any(Training.class), eq(TrainingDto.class));
    }

    @Test
    void getTrainerTrainings_Success() {
        String username = "testUser";
        Date fromDate = new Date();
        Date toDate = new Date();
        List<TrainingDto> trainingDtos = List.of(new TrainingDto());

        when(trainingRepository.findAllByTrainerUserUsernameAndTrainingDateBetween(username, fromDate, toDate))
                .thenReturn(List.of(new Training()));
        when(conversionService.convert(any(Training.class), eq(TrainingDto.class))).thenReturn(new TrainingDto());

        List<TrainingDto> result = trainingService.getTrainerTrainings(username, fromDate, toDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingRepository, times(1)).findAllByTrainerUserUsernameAndTrainingDateBetween(username, fromDate, toDate);
        verify(conversionService, times(1)).convert(any(Training.class), eq(TrainingDto.class));
    }
}