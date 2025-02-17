package com.lab.epam.crm.gym.config.util;

import com.lab.epam.crm.gym.entity.TrainingType;
import com.lab.epam.crm.gym.repository.TrainingTypeRepository;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AppBeanPostProcessor implements BeanPostProcessor {
    private static final List<String> DEFAULT_TRAINING_TYPES = Arrays.asList(
            "CARDIO_TRAINING",
            "STRENGTH_TRAINING",
            "FLEXIBILITY_TRAINING",
            "BALANCE_TRAINING",
            "DIET_CONSULTATION",
            "WEIGHT_LOSS_PILATES_TRAINING",
            "YOGA_TRAINING",
            "DANCE_TRAINING"
    );

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof TrainingTypeRepository repository) {
            seedTrainingTypes(repository);
        }
        return bean;
    }

    private void seedTrainingTypes(TrainingTypeRepository repository) {
        DEFAULT_TRAINING_TYPES.forEach(trainingTypeName -> {
            if (!repository.existsByTrainingTypeName(trainingTypeName)) {
                TrainingType trainingType = new TrainingType();
                trainingType.setTrainingTypeName(trainingTypeName);
                repository.save(trainingType);
            }
        });
    }
}
