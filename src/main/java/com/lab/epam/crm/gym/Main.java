package com.lab.epam.crm.gym;

import com.lab.epam.crm.gym.config.AppConfig;
import com.lab.epam.crm.gym.config.HibernateConfig;
import com.lab.epam.crm.gym.config.LogbackConfig;
import com.lab.epam.crm.gym.dto.TraineeDto;
import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.dto.TrainingDto;
import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.service.TraineeService;
import com.lab.epam.crm.gym.service.TrainerService;
import com.lab.epam.crm.gym.service.TrainingService;
import com.lab.epam.crm.gym.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(AppConfig.class, LogbackConfig.class, HibernateConfig.class);
            context.refresh();

            // Get services from context
            TraineeService traineeService = context.getBean(TraineeService.class);
            TrainerService trainerService = context.getBean(TrainerService.class);
            TrainingService trainingService = context.getBean(TrainingService.class);
            UserService userService = context.getBean(UserService.class);

            // Create Trainee
            TraineeDto createdTrainee = createTrainee(traineeService, userService);
            System.out.println("Created Trainee: " + createdTrainee);

            // Create Trainers
            List<Integer> trainerIds = createTrainers(trainerService);

            // Update Trainee's Trainers List
            TraineeDto updatedTrainee = traineeService.updateTraineeTrainersList(createdTrainee.getId(), trainerIds);
            System.out.println("Updated Trainee with Trainers: " + updatedTrainee);

            // Create and Get Training
            TrainingDto createdTraining = createTraining(trainingService, updatedTrainee, trainerIds.get(0));
            System.out.println("Created Training: " + createdTraining);

            TrainingDto fetchedTraining = trainingService.getTrainingById(createdTraining.getId());
            System.out.println("Fetched Training: " + fetchedTraining);

        } catch (Exception e) {
            System.out.println(e.getMessage() + " It's very sad :(");
        }
    }

    private static TraineeDto createTrainee(TraineeService traineeService, UserService userService) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");

        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setUser(userRequestDto);

        // Create Trainee
        return traineeService.createTrainee(traineeDto);
    }

    private static List<Integer> createTrainers(TrainerService trainerService) {
        // Create some trainers
        TrainerDto trainer1 = createTrainer(trainerService, "Jane", "Smith");
        TrainerDto trainer2 = createTrainer(trainerService, "Bob", "Johnson");

        return Arrays.asList(trainer1.getId(), trainer2.getId());
    }

    private static TrainerDto createTrainer(TrainerService trainerService, String firstName, String lastName) {
        UserRequestDto trainerUserRequestDto = new UserRequestDto();
        trainerUserRequestDto.setFirstName(firstName);
        trainerUserRequestDto.setLastName(lastName);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setUser(trainerUserRequestDto);

        // Create Trainer
        return trainerService.createTrainer(trainerDto);
    }

    private static TrainingDto createTraining(TrainingService trainingService, TraineeDto traineeDto, Integer trainerId) {
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTrainee(traineeDto);
        trainingDto.setId(trainerId);
        trainingDto.setTrainingDate(new Date());

        // Create Training
        return trainingService.createTraining(trainingDto);
    }
}