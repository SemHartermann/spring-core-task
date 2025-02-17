package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.TrainerRequestDto;
import com.lab.epam.crm.gym.dto.TrainerResponseDto;
import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.dto.UserResponseDto;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.User;
import com.lab.epam.crm.gym.repository.TrainerRepository;
import com.lab.epam.crm.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserService userService;

    @Mock
    private ConversionService conversionService;

    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainerService = new TrainerServiceImpl(trainerRepository, userService, conversionService);
    }

    @Test
    void createTrainer_Success() {
        TrainerRequestDto trainerRequestDto = new TrainerRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Jane");
        userRequestDto.setLastName("Smith");
        trainerRequestDto.setUser(userRequestDto);

        Trainer trainer = new Trainer();
        UserResponseDto userResponseDto = new UserResponseDto();
        User user = new User();
        Trainer savedTrainer = new Trainer();

        when(conversionService.convert(trainerRequestDto, Trainer.class)).thenReturn(trainer);
        when(userService.createUser(userRequestDto)).thenReturn(userResponseDto);
        when(conversionService.convert(userResponseDto, User.class)).thenReturn(user);
        when(trainerRepository.save(trainer)).thenReturn(savedTrainer);
        when(conversionService.convert(savedTrainer, TrainerResponseDto.class)).thenReturn(new TrainerResponseDto());

        TrainerResponseDto result = trainerService.createTrainer(trainerRequestDto);

        assertNotNull(result);
        verify(trainerRepository, times(1)).save(trainer);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
        verify(userService, times(1)).createUser(userRequestDto);
    }

    @Test
    void getTrainerByUsername_Success() {
        String username = "testUser";
        Trainer trainer = new Trainer();
        TrainerResponseDto trainerResponseDto = new TrainerResponseDto();

        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));
        when(conversionService.convert(trainer, TrainerResponseDto.class)).thenReturn(trainerResponseDto);

        TrainerResponseDto result = trainerService.getTrainerByUsername(username);

        assertNotNull(result);
        verify(trainerRepository, times(1)).findByUserUsername(username);
        verify(conversionService, times(1)).convert(trainer, TrainerResponseDto.class);
    }

    @Test
    void updateTrainerProfile_Success() {
        TrainerRequestDto trainerRequestDto = new TrainerRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Jane");
        userRequestDto.setLastName("Smith");
        trainerRequestDto.setUser(userRequestDto);

        Trainer trainer = new Trainer();
        Trainer updatedTrainer = new Trainer();

        when(conversionService.convert(trainerRequestDto, Trainer.class)).thenReturn(trainer);
        when(trainerRepository.save(trainer)).thenReturn(updatedTrainer);
        when(conversionService.convert(updatedTrainer, TrainerResponseDto.class)).thenReturn(new TrainerResponseDto());

        TrainerResponseDto result = trainerService.updateTrainerProfile(trainerRequestDto);

        assertNotNull(result);
        verify(userService, times(1)).checkIsActive(userRequestDto);
        verify(trainerRepository, times(1)).save(trainer);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void updateTrainerPassword_Success() {
        TrainerRequestDto trainerRequestDto = new TrainerRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Jane");
        userRequestDto.setLastName("Smith");
        trainerRequestDto.setUser(userRequestDto);

        Trainer trainer = new Trainer();
        Trainer updatedTrainer = new Trainer();

        when(conversionService.convert(trainerRequestDto, Trainer.class)).thenReturn(trainer);
        when(trainerRepository.save(trainer)).thenReturn(updatedTrainer);
        when(conversionService.convert(updatedTrainer, TrainerResponseDto.class)).thenReturn(new TrainerResponseDto());

        TrainerResponseDto result = trainerService.updateTrainerPassword(trainerRequestDto, "newPassword");

        assertNotNull(result);
        verify(userService, times(1)).checkIsActive(userRequestDto);
        verify(trainerRepository, times(1)).save(trainer);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void activateTrainer_Success() {
        TrainerRequestDto trainerRequestDto = new TrainerRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Jane");
        userRequestDto.setLastName("Smith");
        trainerRequestDto.setUser(userRequestDto);

        UserResponseDto userResponseDto = new UserResponseDto();

        when(userService.activateUser(userRequestDto)).thenReturn(userResponseDto);
        when(conversionService.convert(trainerRequestDto, TrainerResponseDto.class)).thenReturn(new TrainerResponseDto());

        TrainerResponseDto result = trainerService.activateTrainer(trainerRequestDto);

        assertNotNull(result);
        verify(userService, times(1)).activateUser(userRequestDto);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }

    @Test
    void deactivateTrainer_Success() {
        TrainerRequestDto trainerRequestDto = new TrainerRequestDto();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Jane");
        userRequestDto.setLastName("Smith");
        trainerRequestDto.setUser(userRequestDto);

        UserResponseDto userResponseDto = new UserResponseDto();

        when(userService.deactivateUser(userRequestDto)).thenReturn(userResponseDto);
        when(conversionService.convert(trainerRequestDto, TrainerResponseDto.class)).thenReturn(new TrainerResponseDto());

        TrainerResponseDto result = trainerService.deactivateTrainer(trainerRequestDto);

        assertNotNull(result);
        verify(userService, times(1)).deactivateUser(userRequestDto);
        verify(conversionService, times(2)).convert(any(), (TypeDescriptor) any());
    }
}