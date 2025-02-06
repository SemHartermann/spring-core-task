package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TraineeDao;
import com.lab.epam.crm.gym.dto.TraineeDto;
import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.mapper.TraineeMapper;
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
public class TraineeServiceImplTest {
    @Mock
    private TraineeDao traineeDao;

    @Mock
    private TraineeMapper traineeMapper;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    public void setup() {
        Mockito.lenient().when(userProfileService.generateUsername(any())).thenReturn("jane.smith1");
        Mockito.lenient().when(userProfileService.generateRandomPassword()).thenReturn("password");
    }

    @Test
    public void testCreateTrainee() {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("Jane");
        traineeDto.setLastName("Smith");

        Trainee trainee = new Trainee();
        when(traineeMapper.toEntity(any(TraineeDto.class))).thenReturn(trainee);

        traineeService.create(traineeDto);

        verify(traineeDao, times(1)).save(any(Trainee.class));
    }

    @Test
    public void testGetTraineeById() {
        Trainee trainee = new Trainee();
        when(traineeDao.findById(anyInt())).thenReturn(trainee);

        traineeService.getById(1);

        verify(traineeDao, times(1)).findById(1);
        verify(traineeMapper, times(1)).toDto(trainee);

        TraineeDto expected = new TraineeDto();
        when(traineeMapper.toDto(any())).thenReturn(expected);

        TraineeDto actual = traineeService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateTrainee() {
        TraineeDto traineeDto = new TraineeDto();
        Trainee trainee = new Trainee();
        when(traineeMapper.toEntity(any(TraineeDto.class))).thenReturn(trainee);

        traineeService.update(traineeDto);

        verify(traineeDao, times(1)).update(trainee);
    }

    @Test
    public void testDeleteTraineeById() {
        traineeService.deleteById(1);

        verify(traineeDao, times(1)).deleteById(1);
    }

    @Test
    public void testGetAllTrainees() {
        List<Trainee> trainees = new ArrayList<>();
        when(traineeDao.findAll()).thenReturn(trainees);

        traineeService.getAll();

        verify(traineeDao, times(1)).findAll();
        verify(traineeMapper, times(1)).toDtoList(trainees);
    }
}