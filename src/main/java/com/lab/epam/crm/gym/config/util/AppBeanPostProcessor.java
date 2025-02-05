package com.lab.epam.crm.gym.config.util;

import com.lab.epam.crm.gym.dao.storage.InMemoryStorage;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AppBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof InMemoryStorage storage) {
            storage.init();
        }
        return bean;
    }
}
