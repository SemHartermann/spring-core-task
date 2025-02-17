package com.lab.epam.crm.gym;

import com.lab.epam.crm.gym.config.AppConfig;
import com.lab.epam.crm.gym.config.HibernateConfig;
import com.lab.epam.crm.gym.config.LogbackConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(AppConfig.class);
            context.register(LogbackConfig.class);
            context.register(HibernateConfig.class);
            context.refresh();

        } catch (Exception e) {
            System.out.println(e.getMessage() + " It's very sad :(");
        }
    }
}