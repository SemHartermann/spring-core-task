package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dao.TraineeDao;
import com.lab.epam.crm.gym.dao.TrainerDao;
import com.lab.epam.crm.gym.dto.UserDto;
import com.lab.epam.crm.gym.entity.Trainee;
import com.lab.epam.crm.gym.entity.Trainer;
import com.lab.epam.crm.gym.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserProfileService {
    static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static SecureRandom RANDOM = new SecureRandom();

    TraineeDao traineeDao;
    TrainerDao trainerDao;

    protected String generateUsername(UserDto userDto) {
        String baseUsername = userDto.getFirstName() + "." + userDto.getLastName();
        int serialNumber = 0;
        String newUsername = baseUsername;

        List<Trainee> trainees = traineeDao.findAll();
        List<Trainer> trainers = trainerDao.findAll();

        List<User> allUsers = new ArrayList<>(trainees);
        allUsers.addAll(trainers);

        String finalNewUsername = newUsername;
        while (allUsers.stream()
                .anyMatch(u -> u.getUsername().equals(finalNewUsername))) {
            serialNumber++;
            newUsername = baseUsername + serialNumber;
        }

        return newUsername;
    }

    protected String generateRandomPassword() {
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return sb.toString();
    }
}
