package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.*;
import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.User;
import com.lab.epam.crm.gym.repository.TraineeRepository;
import com.lab.epam.crm.gym.repository.TrainerRepository;
import com.lab.epam.crm.gym.repository.TrainingRepository;
import com.lab.epam.crm.gym.service.TraineeService;
import com.lab.epam.crm.gym.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TraineeServiceImpl implements TraineeService {

    TraineeRepository traineeRepository;
    TrainingRepository trainingRepository;
    TrainerRepository trainerRepository;
    UserService userService;
    ConversionService conversionService;

    @Transactional
    @Override
    public TraineeResponseDto createTrainee(TraineeRequestDto traineeRequestDto) {
        log.trace("Creating trainee with first name: {} and last name: {}",
                traineeRequestDto.getUser().getFirstName(), traineeRequestDto.getUser().getLastName());

        Trainee trainee = conversionService.convert(traineeRequestDto, Trainee.class);

        UserResponseDto userDto = userService.createUser(traineeRequestDto.getUser());

        trainee.setUser(conversionService.convert(userDto, User.class));

        Trainee savedTrainee = traineeRepository.save(trainee);

        log.debug("Trainee created with user: {}", userDto.getUsername());

        return conversionService.convert(savedTrainee, TraineeResponseDto.class);
    }

    @Override
    public TraineeResponseDto authenticate(String username, String password) {
        log.trace("Authenticating trainee with username: {}", username);

        userService.authenticate(username, password);

        Trainee trainee = traineeRepository.findByUserUsername(username).get();

        return conversionService.convert(trainee, TraineeResponseDto.class);
    }

    @Override
    public TraineeResponseDto getTraineeByUsername(String username) {
        log.trace("Fetching trainee by username: {}", username);

        Trainee trainee = traineeRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        log.debug("Trainee was found by username: {}", username);

        return conversionService.convert(trainee, TraineeResponseDto.class);
    }

    @Override
    public TraineeResponseDto getTraineeById(Integer id) {
        log.trace("Fetching trainee by id: {}", id);

        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        log.debug("Trainee was found by id: {}", id);

        return conversionService.convert(trainee, TraineeResponseDto.class);
    }

    @Override
    public TraineeResponseDto updateTraineeProfile(TraineeRequestDto traineeRequestDto) {
        log.trace("Updating profile for trainee: {}", traineeRequestDto.getUser().getUsername());

        userService.checkIsActive(traineeRequestDto.getUser());

        Trainee trainee = conversionService.convert(traineeRequestDto, Trainee.class);
        Trainee updatedTrainee = traineeRepository.save(Objects.requireNonNull(trainee));

        log.debug("Profile updated for trainee: {}", traineeRequestDto.getUser().getUsername());

        return conversionService.convert(updatedTrainee, TraineeResponseDto.class);
    }

    @Override
    public TraineeResponseDto updateTraineePassword(TraineeRequestDto traineeRequestDto, String newPassword) {
        log.trace("Updating password for trainee: {}", traineeRequestDto.getUser().getUsername());

        userService.checkIsActive(traineeRequestDto.getUser());

        Trainee trainee = conversionService.convert(traineeRequestDto, Trainee.class);
        Objects.requireNonNull(trainee).getUser().setPassword(newPassword);
        Trainee updatedTrainee = traineeRepository.save(trainee);

        log.debug("Password updated for trainee: {}", traineeRequestDto.getUser().getUsername());

        return conversionService.convert(updatedTrainee, TraineeResponseDto.class);
    }

    @Override
    public TraineeResponseDto activateTrainee(TraineeRequestDto traineeRequestDto) {
        log.trace("Activating trainee: {}", traineeRequestDto.getUser().getUsername());

        UserResponseDto userResponseDto = userService.activateUser(traineeRequestDto.getUser());

        TraineeResponseDto traineeResponseDto = conversionService.convert(traineeRequestDto,
                TraineeResponseDto.class);
        traineeResponseDto.setUser(userResponseDto);

        log.debug("Trainee activated: {}", traineeResponseDto.getUser().getUsername());

        return traineeResponseDto;
    }

    @Override
    public TraineeResponseDto deactivateTrainee(TraineeRequestDto traineeRequestDto) {
        log.trace("Deactivating trainee: {}", traineeRequestDto.getUser().getUsername());

        UserResponseDto userResponseDto = userService.deactivateUser(traineeRequestDto.getUser());

        TraineeResponseDto traineeResponseDto = conversionService.convert(traineeRequestDto,
                TraineeResponseDto.class);
        traineeResponseDto.setUser(userResponseDto);

        log.debug("Trainee deactivated: {}", traineeRequestDto.getUser().getUsername());

        return traineeResponseDto;
    }

    @Override
    public void deleteTraineeProfileByUsername(String username) {
        log.trace("Deleting trainee profile by username: {}", username);

        TraineeResponseDto traineeResponseDto = getTraineeByUsername(username);
        userService.checkIsActive(conversionService.convert(traineeResponseDto.getUser(), UserRequestDto.class));

        traineeRepository.deleteByUserUsername(username);

        log.debug("Trainee profile deleted: {}", username);
    }

    @Override
    public List<TrainingDto> getTraineeTrainings(String username, Date fromDate, Date toDate, String trainerName, String trainingType) {
        log.trace("Fetching trainings for trainee: {} from date: {} to date: {}", username, fromDate, toDate);

        TraineeResponseDto traineeResponseDto = getTraineeByUsername(username);
        userService.checkIsActive(conversionService.convert(traineeResponseDto.getUser(), UserRequestDto.class));

        return trainingRepository.findAllByTrainerUserUsernameAndTrainingDateBetween(username, fromDate, toDate)
                .stream()
                .map(training -> conversionService.convert(training, TrainingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainerResponseDto> getUnassignedTrainers(String username) {
        log.trace("Fetching unassigned trainers for trainee: {}", username);

        TraineeResponseDto traineeResponseDto = getTraineeByUsername(username);
        userService.checkIsActive(conversionService.convert(traineeResponseDto.getUser(), UserRequestDto.class));

        return trainerRepository.findUnassignedTrainersByTraineeUsername(username).stream()
                .map(trainer -> conversionService.convert(trainer, TrainerResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TraineeResponseDto updateTraineeTrainersList(Integer traineeId, List<Integer> trainerIds) {
        log.trace("Updating trainer list for trainee ID: {}", traineeId);

        TraineeResponseDto traineeResponseDto = getTraineeById(traineeId);
        userService.checkIsActive(conversionService.convert(traineeResponseDto.getUser(), UserRequestDto.class));

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trainee ID"));
        trainee.getTrainers().clear();

        trainerIds.forEach(trainerId -> {
            Trainer trainer = trainerRepository.findById(trainerId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid trainer ID"));
            trainee.getTrainers().add(trainer);
        });
        Trainee updatedTrainee = traineeRepository.save(trainee);

        log.debug("Trainer list updated for trainee ID: {}", traineeId);

        return conversionService.convert(updatedTrainee, TraineeResponseDto.class);
    }
}