package com.lab.epam.crm.gym.config.util;

import com.lab.epam.crm.gym.dao.storage.TraineeStorage;
import com.lab.epam.crm.gym.dao.storage.TrainerStorage;
import com.lab.epam.crm.gym.dao.storage.TrainingStorage;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AppBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof TraineeStorage storage) {
            storage.init();
        }

        if (bean instanceof TrainerStorage storage) {
            storage.init();
        }

        if (bean instanceof TrainingStorage storage) {
            storage.init();
        }

        return bean;
    }
}
