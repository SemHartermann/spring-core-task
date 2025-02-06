package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TraineeDao;
import com.lab.epam.crm.gym.dto.TraineeDto;
import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.mapper.TraineeMapper;
import com.lab.epam.crm.gym.service.TraineeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@DependsOn({"serviceLogger", "rootLogger"})
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    UserProfileService userProfileService;

    TraineeDao traineeDao;

    TraineeMapper traineeMapper;

    @Override
    public void create(TraineeDto traineeDto) {
        if (traineeDto.getFirstName().isBlank() || traineeDto.getLastName().isBlank()) {
            log.error("Error creating trainee: {}", traineeDto);

            throw new IllegalArgumentException("First name and last name cannot be empty");
        }

        log.debug("Creating trainee: {}", traineeDto);

        Trainee trainee = traineeMapper.toEntity(traineeDto);

        trainee.setUsername(userProfileService.generateUsername(traineeDto));
        trainee.setPassword(userProfileService.generateRandomPassword());

        traineeDao.save(trainee);
    }

    @Override
    public void update(TraineeDto traineeDto) {
        if (traineeDto.getFirstName().isBlank() || traineeDto.getLastName().isBlank()) {
            log.error("Error updating trainee: {}", traineeDto);

            throw new IllegalArgumentException("First name and last name cannot be empty");
        }

        log.debug("Updating trainee: {}", traineeDto);

        Trainee trainee = traineeMapper.toEntity(traineeDto);

        traineeDao.update(trainee);
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("Deleting trainee by ID: {}", id);

        traineeDao.deleteById(id);
    }

    @Override
    public TraineeDto getById(Integer id) {
        log.debug("Finding trainee by ID: {}", id);

        Trainee trainee = traineeDao.findById(id);

        return traineeMapper.toDto(trainee);
    }

    @Override
    public List<TraineeDto> getAll() {
        log.debug("Finding all trainees");

        List<Trainee> trainees = traineeDao.findAll();

        return traineeMapper.toDtoList(trainees);
    }
}