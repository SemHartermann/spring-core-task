package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TrainingDao;
import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.entity.Training;
import com.lab.epam.crm.gym.mapper.TrainingMapper;
import com.lab.epam.crm.gym.service.TrainingService;
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
public class TrainingServiceImpl implements TrainingService {

    TrainingDao trainingDao;
    TrainingMapper trainingMapper;

    @Override
    public void create(TrainingDto trainingDto) {
        log.debug("Creating training: {}", trainingDto);

        Training training = trainingMapper.toEntity(trainingDto);
        trainingDao.save(training);
    }

    @Override
    public TrainingDto getById(Integer id) {
        log.debug("Finding training by ID: {}", id);

        Training training = trainingDao.findById(id);

        return trainingMapper.toDto(training);
    }

    @Override
    public List<TrainingDto> getAll() {
        log.debug("Finding all trainings");

        List<Training> trainings = trainingDao.findAll();

        return trainingMapper.toDtoList(trainings);
    }
}
