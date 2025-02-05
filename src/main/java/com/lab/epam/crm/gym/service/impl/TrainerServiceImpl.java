package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TrainerDao;
import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.mapper.TrainerMapper;
import com.lab.epam.crm.gym.service.TrainerService;
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
public class TrainerServiceImpl implements TrainerService {
    UserProfileService userProfileService;

    TrainerDao trainerDao;

    TrainerMapper trainerMapper;

    @Override
    public void create(TrainerDto trainerDto) {
        log.debug("Creating trainer: {}", trainerDto);

        Trainer trainer = trainerMapper.toEntity(trainerDto);
        
        trainer.setUsername(userProfileService.generateUsername(trainerDto));
        trainer.setPassword(userProfileService.generateRandomPassword());

        trainerDao.save(trainer);
    }

    @Override
    public void update(TrainerDto trainerDto) {
        log.debug("Updating trainer: {}", trainerDto);

        Trainer trainer = trainerMapper.toEntity(trainerDto);
        
        trainerDao.update(trainer);
    }

    @Override
    public TrainerDto getById(Integer id) {
        log.debug("Finding trainer by ID: {}", id);
        Trainer trainer = trainerDao.findById(id);
        
        return trainerMapper.toDto(trainer);
    }

    @Override
    public List<TrainerDto> getAll() {
        log.debug("Finding all trainers");
        
        List<Trainer> trainers = trainerDao.findAll();
        
        return trainerMapper.toDtoList(trainers);
    }
}
