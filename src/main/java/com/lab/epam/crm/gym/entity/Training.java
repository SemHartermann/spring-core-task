package com.lab.epam.crm.gym.entity;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class Training {
    Integer id;

    Integer traineeId;

    Integer trainerId;

    String name;

    TrainingType type;

    String date;

    int duration;
}