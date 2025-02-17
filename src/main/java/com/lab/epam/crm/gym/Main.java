package com.lab.epam.crm.gym;

import com.lab.epam.crm.gym.config.AppConfig;
import com.lab.epam.crm.gym.config.HibernateConfig;
import com.lab.epam.crm.gym.config.LogbackConfig;
import com.lab.epam.crm.gym.dto.*;
import com.lab.epam.crm.gym.service.TraineeService;
import com.lab.epam.crm.gym.service.TrainerService;
import com.lab.epam.crm.gym.service.TrainingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(AppConfig.class);
            context.register(LogbackConfig.class);
            context.register(HibernateConfig.class);
            context.refresh();

            // Get services from context
            TraineeService traineeService = context.getBean(TraineeService.class);
            TrainerService trainerService = context.getBean(TrainerService.class);
            TrainingService trainingService = context.getBean(TrainingService.class);

            // Create Trainee
            TraineeResponseDto createdTrainee = createTrainee(traineeService);
            System.out.println("Created Trainee: " + createdTrainee);

            // Create Trainers
            List<TrainerResponseDto> createdTrainers = createTrainers(trainerService);

            // Update Trainee's Trainers List
            List<Integer> trainerIds = createdTrainers.stream().map(TrainerResponseDto::getId).collect(Collectors.toList());
            TraineeResponseDto updatedTrainee = traineeService.updateTraineeTrainersList(createdTrainee.getId(), trainerIds);
            System.out.println("Updated Trainee with Trainers: " + updatedTrainee);

            // Create and Get Training
            TrainingDto createdTraining = createTraining(trainingService, updatedTrainee, createdTrainers.get(0));
            System.out.println("Created Training: " + createdTraining);

            TrainingDto fetchedTraining = trainingService.getTrainingById(createdTraining.getId());
            System.out.println("Fetched Training: " + fetchedTraining);

        } catch (Exception e) {
            System.out.println(e.getMessage() + " It's very sad :(");
        }
    }

    private static TraineeResponseDto createTrainee(TraineeService traineeService) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");

        TraineeRequestDto traineeRequestDto = new TraineeRequestDto();
        traineeRequestDto.setUser(userRequestDto);

        // Create Trainee
        return traineeService.createTrainee(traineeRequestDto);
    }

    private static List<TrainerResponseDto> createTrainers(TrainerService trainerService) {
        // Create some trainers
        TrainerResponseDto trainer1 = createTrainer(trainerService, "Jane", "Smith");
        TrainerResponseDto trainer2 = createTrainer(trainerService, "Bob", "Johnson");

        return Arrays.asList(trainer1, trainer2);
    }

    private static TrainerResponseDto createTrainer(TrainerService trainerService, String firstName, String lastName) {
        UserRequestDto trainerUserRequestDto = new UserRequestDto();
        trainerUserRequestDto.setFirstName(firstName);
        trainerUserRequestDto.setLastName(lastName);

        TrainerRequestDto trainerRequestDto = new TrainerRequestDto();
        trainerRequestDto.setUser(trainerUserRequestDto);

        // Create Trainer
        return trainerService.createTrainer(trainerRequestDto);
    }

    private static TrainingDto createTraining(TrainingService trainingService, TraineeResponseDto traineeResponseDto, TrainerResponseDto trainerResponseDto) {
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTrainee(traineeResponseDto);
        trainingDto.setTrainer(trainerResponseDto);
        trainingDto.setTrainingName("Sample Training");
        trainingDto.setTrainingDate(new Date());
        trainingDto.setTrainingDuration(60); // Duration in minutes

        // Create Training
        return trainingService.createTraining(trainingDto);
    }
}