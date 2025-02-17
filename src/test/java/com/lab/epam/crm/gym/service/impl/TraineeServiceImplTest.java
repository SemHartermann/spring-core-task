package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.*;
import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.entity.Training;
import com.lab.epam.crm.gym.entity.User;
import com.lab.epam.crm.gym.repository.TraineeRepository;
import com.lab.epam.crm.gym.repository.TrainerRepository;
import com.lab.epam.crm.gym.repository.TrainingRepository;
import com.lab.epam.crm.gym.service.UserService;
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

class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserService userService;

    @Mock
    private ConversionService conversionService;

    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        traineeService = new TraineeServiceImpl(traineeRepository, trainingRepository, trainerRepository, userService, conversionService);
    }

    @Test
    void createTrainee_Success() {
        TraineeRequestDto traineeRequestDto = new TraineeRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");
        traineeRequestDto.setUser(userRequestDto);

        Trainee trainee = new Trainee();
        UserResponseDto userResponseDto = new UserResponseDto();
        User user = new User();
        Trainee savedTrainee = new Trainee();

        when(conversionService.convert(traineeRequestDto, Trainee.class)).thenReturn(trainee);
        when(userService.createUser(userRequestDto)).thenReturn(userResponseDto);
        when(conversionService.convert(userResponseDto, User.class)).thenReturn(user);
        when(traineeRepository.save(trainee)).thenReturn(savedTrainee);
        when(conversionService.convert(savedTrainee, TraineeResponseDto.class)).thenReturn(new TraineeResponseDto());

        TraineeResponseDto result = traineeService.createTrainee(traineeRequestDto);

        assertNotNull(result);
        verify(traineeRepository, times(1)).save(trainee);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
        verify(userService, times(1)).createUser(userRequestDto);
    }

    @Test
    void getTraineeByUsername_Success() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        TraineeResponseDto traineeResponseDto = new TraineeResponseDto();

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
        when(conversionService.convert(trainee, TraineeResponseDto.class)).thenReturn(traineeResponseDto);

        TraineeResponseDto result = traineeService.getTraineeByUsername(username);

        assertNotNull(result);
        verify(traineeRepository, times(1)).findByUserUsername(username);
        verify(conversionService, times(1)).convert(trainee, TraineeResponseDto.class);
    }

    @Test
    void updateTraineeProfile_Success() {
        TraineeRequestDto traineeRequestDto = new TraineeRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");
        traineeRequestDto.setUser(userRequestDto);

        Trainee trainee = new Trainee();
        Trainee updatedTrainee = new Trainee();

        when(conversionService.convert(traineeRequestDto, Trainee.class)).thenReturn(trainee);
        when(traineeRepository.save(trainee)).thenReturn(updatedTrainee);
        when(conversionService.convert(updatedTrainee, TraineeResponseDto.class)).thenReturn(new TraineeResponseDto());

        TraineeResponseDto result = traineeService.updateTraineeProfile(traineeRequestDto);

        assertNotNull(result);
        verify(userService, times(1)).checkIsActive(userRequestDto);
        verify(traineeRepository, times(1)).save(trainee);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void updateTraineePassword_Success() {
        TraineeRequestDto traineeRequestDto = new TraineeRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");
        traineeRequestDto.setUser(userRequestDto);

        Trainee trainee = new Trainee();
        Trainee updatedTrainee = new Trainee();

        when(conversionService.convert(traineeRequestDto, Trainee.class)).thenReturn(trainee);
        when(traineeRepository.save(trainee)).thenReturn(updatedTrainee);
        when(conversionService.convert(updatedTrainee, TraineeResponseDto.class)).thenReturn(new TraineeResponseDto());

        TraineeResponseDto result = traineeService.updateTraineePassword(traineeRequestDto, "newPassword");

        assertNotNull(result);
        verify(userService, times(1)).checkIsActive(userRequestDto);
        verify(traineeRepository, times(1)).save(trainee);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void activateTrainee_Success() {
        TraineeRequestDto traineeRequestDto = new TraineeRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");
        traineeRequestDto.setUser(userRequestDto);

        UserResponseDto userResponseDto = new UserResponseDto();

        when(userService.activateUser(userRequestDto)).thenReturn(userResponseDto);
        when(conversionService.convert(traineeRequestDto, TraineeResponseDto.class)).thenReturn(new TraineeResponseDto());

        TraineeResponseDto result = traineeService.activateTrainee(traineeRequestDto);

        assertNotNull(result);
        verify(userService, times(1)).activateUser(userRequestDto);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void deactivateTrainee_Success() {
        TraineeRequestDto traineeRequestDto = new TraineeRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");
        traineeRequestDto.setUser(userRequestDto);

        UserResponseDto userResponseDto = new UserResponseDto();

        when(userService.deactivateUser(userRequestDto)).thenReturn(userResponseDto);
        when(conversionService.convert(traineeRequestDto, TraineeResponseDto.class)).thenReturn(new TraineeResponseDto());

        TraineeResponseDto result = traineeService.deactivateTrainee(traineeRequestDto);

        assertNotNull(result);
        verify(userService, times(1)).deactivateUser(userRequestDto);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void getTraineeTrainings_Success() {
        String username = "testUser";
        Date fromDate = new Date();
        Date toDate = new Date();
        List<TrainingDto> trainingDtos = List.of(new TrainingDto());

        Trainee trainee = new Trainee();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername(username);

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
        when(trainingRepository.findAllByTrainerUserUsernameAndTrainingDateBetween(username, fromDate, toDate))
                .thenReturn(List.of(new Training()));
        when(conversionService.convert(any(Training.class), eq(TrainingDto.class))).thenReturn(new TrainingDto());

        List<TrainingDto> result = traineeService.getTraineeTrainings(username, fromDate, toDate, null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userService, times(1)).checkIsActive(any(UserRequestDto.class));
        verify(trainingRepository, times(1)).findAllByTrainerUserUsernameAndTrainingDateBetween(username, fromDate, toDate);
        verify(conversionService, times(1)).convert(any(Training.class), eq(TrainingDto.class));
    }
}