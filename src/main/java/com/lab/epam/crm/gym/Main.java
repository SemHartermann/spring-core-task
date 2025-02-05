package com.lab.epam.crm.gym;

import com.lab.epam.crm.gym.config.AppConfig;
import com.lab.epam.crm.gym.config.LogbackConfig;
import com.lab.epam.crm.gym.dao.TrainerDao;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(AppConfig.class);
            context.register(LogbackConfig.class);
            context.refresh();

            TrainerDao trainerDao = context.getBean(TrainerDao.class);

            Trainer trainer1 = new Trainer();
            trainer1.setId(1);
            trainer1.setFirstName("John");
            trainer1.setLastName("Doe");
            trainer1.setUsername("john.doe");
            trainer1.setPassword("pass123");
            trainer1.setActive(true);
            trainer1.setSpecialization(TrainingType.CARDIO);

            Trainer trainer2 = new Trainer();
            trainer2.setId(2);
            trainer2.setFirstName("Jane");
            trainer2.setLastName("Smith");
            trainer2.setUsername("jane.smith");
            trainer2.setPassword("pass456");
            trainer2.setActive(true);
            trainer2.setSpecialization(TrainingType.STRENGTH_TRAINING);

            trainerDao.save(trainer1);
            trainerDao.save(trainer2);

        } catch (Exception e) {
            log.error("Exception during application startup", e);
        }
    }
}