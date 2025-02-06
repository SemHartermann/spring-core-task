package com.lab.epam.crm.gym.config.util;

import com.lab.epam.crm.gym.config.StorageConfig;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AppBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("storageConfig")) {
            ((StorageConfig) bean).init();
        }
        return bean;
    }
}
