package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.repository.TrainerRepository;
import com.lab.epam.crm.gym.service.TrainerService;
import com.lab.epam.crm.gym.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainerServiceImpl implements TrainerService {
    TrainerRepository trainerRepository;
    UserService userService;
    ConversionService conversionService;

    @Transactional
    @Override
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        log.trace("Creating trainer with first name: {} and last name: {}", trainerDto.getUser().getFirstName(), trainerDto.getUser().getLastName());

        UserRequestDto userRequestDto = userService.createUser(trainerDto.getUser());

        trainerDto.setUser(userRequestDto);

        Trainer trainer = conversionService.convert(trainerDto, Trainer.class);
        trainer = trainerRepository.save(Objects.requireNonNull(trainer));

        log.debug("Trainer created with user: {}", userRequestDto.getUsername());

        return conversionService.convert(trainer, TrainerDto.class);
    }

    @Override
    public TrainerDto getTrainerById(Integer id) {
        log.trace("Fetching trainer by id: {}", id);

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        log.debug("Trainer was found by id: {}", id);

        return conversionService.convert(trainer, TrainerDto.class);
    }

    @Override
    public TrainerDto getTrainerByUsername(String username) {
        log.trace("Fetching trainer by username: {}", username);

        Trainer trainer = trainerRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        log.debug("Trainer was found by username: {}", username);

        return conversionService.convert(trainer, TrainerDto.class);
    }

    @Override
    public TrainerDto authenticate(String username, String password) {
        log.trace("Authenticating trainer with username: {}", username);

        userService.authenticate(username, password);

        Trainer trainer = trainerRepository.findByUserUsername(username).get();

        return conversionService.convert(trainer, TrainerDto.class);
    }

    @Override
    public TrainerDto updateTrainerProfile(TrainerDto trainerDto) {
        log.trace("Updating profile for trainer: {}", trainerDto.getUser().getUsername());

        userService.checkIsActive(trainerDto.getUser());

        Trainer trainer = conversionService.convert(trainerDto, Trainer.class);
        Trainer updatedTrainer = trainerRepository.save(Objects.requireNonNull(trainer));

        log.debug("Profile updated for trainer: {}", trainerDto.getUser().getUsername());

        return conversionService.convert(updatedTrainer, TrainerDto.class);
    }

    @Override
    public TrainerDto updateTrainerPassword(TrainerDto trainerDto, String newPassword) {
        log.trace("Updating password for trainer: {}", trainerDto.getUser().getUsername());

        userService.checkIsActive(trainerDto.getUser());

        Trainer trainer = conversionService.convert(trainerDto, Trainer.class);
        Objects.requireNonNull(trainer).getUser().setPassword(newPassword);
        Trainer updatedTrainer = trainerRepository.save(trainer);

        log.debug("Password updated for trainer: {}", trainerDto.getUser().getUsername());

        return conversionService.convert(updatedTrainer, TrainerDto.class);
    }

    @Override
    public TrainerDto activateTrainer(TrainerDto trainerDto) {
        log.trace("Activating trainer: {}", trainerDto.getUser().getUsername());

        UserRequestDto userRequestDto = userService.activateUser(trainerDto.getUser());
        trainerDto.setUser(userRequestDto);

        log.debug("Trainer activated: {}", trainerDto.getUser().getUsername());

        return trainerDto;
    }

    @Override
    public TrainerDto deactivateTrainer(TrainerDto trainerDto) {
        log.trace("Deactivating trainer: {}", trainerDto.getUser().getUsername());

        userService.checkIsActive(trainerDto.getUser());

        Trainer trainer = trainerRepository.findByUserUsername(trainerDto.getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        trainer.getUser().setIsActive(false);
        Trainer updatedTrainer = trainerRepository.save(trainer);

        log.debug("Trainer deactivated: {}", trainerDto.getUser().getUsername());

        return conversionService.convert(updatedTrainer, TrainerDto.class);
    }
}