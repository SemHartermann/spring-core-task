package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.TraineeDto;
import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.User;
import com.lab.epam.crm.gym.repository.TraineeRepository;
import com.lab.epam.crm.gym.repository.TrainingRepository;
import com.lab.epam.crm.gym.repository.TrainerRepository;
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
    public TraineeDto createTrainee(TraineeDto traineeDto) {
        log.trace("Creating trainee with first name: {} and last name: {}", traineeDto.getUser().getFirstName(), traineeDto.getUser().getLastName());

        Trainee trainee = conversionService.convert(traineeDto, Trainee.class);

        UserRequestDto userDto = userService.createUser(traineeDto.getUser());

        trainee.setUser(conversionService.convert(userDto, User.class));

        Trainee savedTrainee = traineeRepository.save(trainee);

        log.debug("Trainee created with user: {}", userDto.getUsername());

        return conversionService.convert(savedTrainee, TraineeDto.class);
    }

    @Override
    public TraineeDto authenticate(String username, String password) {
        log.trace("Authenticating trainee with username: {}", username);

        userService.authenticate(username, password);

        Trainee trainee = traineeRepository.findByUserUsername(username).get();

        return conversionService.convert(trainee, TraineeDto.class);
    }

    @Override
    public TraineeDto getTraineeByUsername(String username) {
        log.trace("Fetching trainee by username: {}", username);

        Trainee trainee = traineeRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        log.debug("Trainee was found by username: {}", username);

        return conversionService.convert(trainee, TraineeDto.class);
    }

    @Override
    public TraineeDto getTraineeById(Integer id) {
        log.trace("Fetching trainee by id: {}", id);

        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        log.debug("Trainee was found by id: {}", id);

        return conversionService.convert(trainee, TraineeDto.class);
    }

    @Override
    public TraineeDto updateTraineeProfile(TraineeDto traineeDto) {
        log.trace("Updating profile for trainee: {}", traineeDto.getUser().getUsername());

        userService.checkIsActive(traineeDto.getUser());

        Trainee trainee = conversionService.convert(traineeDto, Trainee.class);
        Trainee updatedTrainee = traineeRepository.save(Objects.requireNonNull(trainee));

        log.debug("Profile updated for trainee: {}", traineeDto.getUser().getUsername());

        return conversionService.convert(updatedTrainee, TraineeDto.class);
    }

    @Override
    public TraineeDto updateTraineePassword(TraineeDto traineeDto, String newPassword) {
        log.trace("Updating password for trainee: {}", traineeDto.getUser().getUsername());

        userService.checkIsActive(traineeDto.getUser());

        Trainee trainee = conversionService.convert(traineeDto, Trainee.class);
        Objects.requireNonNull(trainee).getUser().setPassword(newPassword);
        Trainee updatedTrainee = traineeRepository.save(trainee);

        log.debug("Password updated for trainee: {}", traineeDto.getUser().getUsername());

        return conversionService.convert(updatedTrainee, TraineeDto.class);
    }

    @Override
    public TraineeDto activateTrainee(TraineeDto traineeDto) {
        log.trace("Activating trainee: {}", traineeDto.getUser().getUsername());

        UserRequestDto userRequestDto = userService.activateUser(traineeDto.getUser());
        traineeDto.setUser(userRequestDto);

        log.debug("Trainee activated: {}", traineeDto.getUser().getUsername());

        return traineeDto;
    }

    @Override
    public TraineeDto deactivateTrainee(TraineeDto traineeDto) {
        log.trace("Deactivating trainee: {}", traineeDto.getUser().getUsername());

        userService.checkIsActive(traineeDto.getUser());

        Trainee trainee = traineeRepository.findByUserUsername(traineeDto.getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));
        trainee.getUser().setIsActive(false);
        Trainee updatedTrainee = traineeRepository.save(trainee);

        log.debug("Trainee deactivated: {}", traineeDto.getUser().getUsername());

        return conversionService.convert(updatedTrainee, TraineeDto.class);
    }

    @Override
    public void deleteTraineeProfileByUsername(String username) {
        log.trace("Deleting trainee profile by username: {}", username);

        TraineeDto traineeDto = getTraineeByUsername(username);
        userService.checkIsActive(traineeDto.getUser());

        traineeRepository.deleteByUserUsername(username);

        log.debug("Trainee profile deleted: {}", username);
    }

    @Override
    public List<TrainingDto> getTraineeTrainings(String username, Date fromDate, Date toDate, String trainerName, String trainingType) {
        log.trace("Fetching trainings for trainee: {} from date: {} to date: {}", username, fromDate, toDate);

        TraineeDto traineeDto = getTraineeByUsername(username);
        userService.checkIsActive(traineeDto.getUser());

        return trainingRepository.findAllByTrainerUserUsernameAndTrainingDateBetween(username, fromDate, toDate)
                .stream()
                .map(training -> conversionService.convert(training, TrainingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainerDto> getUnassignedTrainers(String username) {
        log.trace("Fetching unassigned trainers for trainee: {}", username);

        TraineeDto traineeDto = getTraineeByUsername(username);
        userService.checkIsActive(traineeDto.getUser());

        return trainerRepository.findUnassignedTrainersByTraineeUsername(username).stream()
                .map(trainer -> conversionService.convert(trainer, TrainerDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TraineeDto updateTraineeTrainersList(Integer traineeId, List<Integer> trainerIds) {
        log.trace("Updating trainer list for trainee ID: {}", traineeId);

        TraineeDto traineeDto = getTraineeById(traineeId);
        userService.checkIsActive(traineeDto.getUser());

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

        return conversionService.convert(updatedTrainee, TraineeDto.class);
    }
}