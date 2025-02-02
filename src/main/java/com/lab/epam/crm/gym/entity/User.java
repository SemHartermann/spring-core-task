package com.lab.epam.crm.gym.entity;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public abstract class User {
    Integer id;

    String firstName;

    String lastName;

    String username;

    String password;

    boolean isActive;
}