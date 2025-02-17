package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.TrainerRequestDto;
import com.lab.epam.crm.gym.dto.TrainerResponseDto;
import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.dto.UserResponseDto;
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
    public TrainerResponseDto createTrainer(TrainerRequestDto trainerRequestDto) {
        log.trace("Creating trainer with first name: {} and last name: {}",
                trainerRequestDto.getUser().getFirstName(), trainerRequestDto.getUser().getLastName());

        UserResponseDto userResponseDto = userService.createUser(trainerRequestDto.getUser());

        trainerRequestDto.setUser(conversionService.convert(userResponseDto, UserRequestDto.class));

        Trainer trainer = conversionService.convert(trainerRequestDto, Trainer.class);
        trainer = trainerRepository.save(Objects.requireNonNull(trainer));

        log.debug("Trainer created with user: {}", userResponseDto.getUsername());

        return conversionService.convert(trainer, TrainerResponseDto.class);
    }

    @Override
    public TrainerResponseDto getTrainerById(Integer id) {
        log.trace("Fetching trainer by id: {}", id);

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        log.debug("Trainer was found by id: {}", id);

        return conversionService.convert(trainer, TrainerResponseDto.class);
    }

    @Override
    public TrainerResponseDto getTrainerByUsername(String username) {
        log.trace("Fetching trainer by username: {}", username);

        Trainer trainer = trainerRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        log.debug("Trainer was found by username: {}", username);

        return conversionService.convert(trainer, TrainerResponseDto.class);
    }

    @Override
    public TrainerResponseDto authenticate(String username, String password) {
        log.trace("Authenticating trainer with username: {}", username);

        userService.authenticate(username, password);

        Trainer trainer = trainerRepository.findByUserUsername(username).get();

        return conversionService.convert(trainer, TrainerResponseDto.class);
    }

    @Override
    public TrainerResponseDto updateTrainerProfile(TrainerRequestDto trainerRequestDto) {
        log.trace("Updating profile for trainer: {}", trainerRequestDto.getUser().getUsername());

        userService.checkIsActive(trainerRequestDto.getUser());

        Trainer trainer = conversionService.convert(trainerRequestDto, Trainer.class);
        Trainer updatedTrainer = trainerRepository.save(Objects.requireNonNull(trainer));

        log.debug("Profile updated for trainer: {}", trainerRequestDto.getUser().getUsername());

        return conversionService.convert(updatedTrainer, TrainerResponseDto.class);
    }

    @Override
    public TrainerResponseDto updateTrainerPassword(TrainerRequestDto trainerRequestDto, String newPassword) {
        log.trace("Updating password for trainer: {}", trainerRequestDto.getUser().getUsername());

        userService.checkIsActive(trainerRequestDto.getUser());

        Trainer trainer = conversionService.convert(trainerRequestDto, Trainer.class);
        Objects.requireNonNull(trainer).getUser().setPassword(newPassword);
        Trainer updatedTrainer = trainerRepository.save(trainer);

        log.debug("Password updated for trainer: {}", trainerRequestDto.getUser().getUsername());

        return conversionService.convert(updatedTrainer, TrainerResponseDto.class);
    }

    @Override
    public TrainerResponseDto activateTrainer(TrainerRequestDto trainerRequestDto) {
        log.trace("Activating trainer: {}", trainerRequestDto.getUser().getUsername());

        UserResponseDto userResponseDto = userService.activateUser(trainerRequestDto.getUser());

        TrainerResponseDto trainerResponseDto = conversionService.convert(trainerRequestDto,
                TrainerResponseDto.class);
        trainerResponseDto.setUser(userResponseDto);

        log.debug("Trainer activated: {}", trainerRequestDto.getUser().getUsername());

        return trainerResponseDto;
    }

    @Override
    public TrainerResponseDto deactivateTrainer(TrainerRequestDto trainerRequestDto) {
        log.trace("Deactivating trainer: {}", trainerRequestDto.getUser().getUsername());

        userService.checkIsActive(trainerRequestDto.getUser());

        UserResponseDto userResponseDto = userService.deactivateUser(trainerRequestDto.getUser());

        TrainerResponseDto trainerResponseDto = conversionService.convert(trainerRequestDto,
                TrainerResponseDto.class);
        trainerResponseDto.setUser(userResponseDto);

        log.debug("Trainer deactivated: {}", trainerRequestDto.getUser().getUsername());

        return trainerResponseDto;
    }
}