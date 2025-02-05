package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TraineeDao;
import com.lab.epam.crm.gym.dao.TrainerDao;
import com.lab.epam.crm.gym.dto.UserDto;
import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.entity.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {
    @Mock
    private TraineeDao traineeDao;

    @Mock
    private TrainerDao trainerDao;

    @InjectMocks
    private UserProfileService userProfileService;

    @BeforeEach
    public void setup() {
        List<Trainee> trainees = Collections.emptyList();
        List<Trainer> trainers = Collections.emptyList();

        Mockito.lenient().when(traineeDao.findAll()).thenReturn(trainees);
        Mockito.lenient().when(trainerDao.findAll()).thenReturn(trainers);
    }

    @Test
    public void testGenerateUsername() {
        UserDto userDto = new UserDto() {
            {
                setFirstName("John");
                setLastName("Doe");
            }
        };

        String username = userProfileService.generateUsername(userDto);

        assertNotNull(username);
        assertFalse(username.isEmpty());
    }

    @Test
    public void testGenerateRandomPassword() {
        String password = userProfileService.generateRandomPassword();

        assertNotNull(password);
        assertEquals(10, password.length());
    }
}