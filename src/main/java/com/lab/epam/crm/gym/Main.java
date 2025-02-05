package com.lab.epam.crm.gym;

import com.lab.epam.crm.gym.config.AppConfig;
import com.lab.epam.crm.gym.config.LogbackConfig;
import com.lab.epam.crm.gym.dao.TrainerDao;
import com.lab.epam.crm.gym.dto.TrainerDto;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.TrainingType;
import com.lab.epam.crm.gym.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(AppConfig.class);
            context.register(LogbackConfig.class);
            context.refresh();

            TrainerService trainerService = context.getBean(TrainerService.class);

            TrainerDto trainer1Dto = new TrainerDto();
            trainer1Dto.setId(1);
            trainer1Dto.setFirstName("John");
            trainer1Dto.setLastName("Doe");
            trainer1Dto.setActive(true);
            trainer1Dto.setSpecialization(TrainingType.CARDIO);

            TrainerDto trainer2Dto = new TrainerDto();
            trainer2Dto.setId(2);
            trainer2Dto.setFirstName("Jane");
            trainer2Dto.setLastName("Smith");
            trainer2Dto.setActive(true);
            trainer2Dto.setSpecialization(TrainingType.STRENGTH_TRAINING);

            trainerService.create(trainer1Dto);
            trainerService.create(trainer2Dto);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "It's very sad :(");
        }
    }
}