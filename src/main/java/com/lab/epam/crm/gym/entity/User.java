package com.lab.epam.crm.gym.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public abstract class User {
    Integer id;

    @NonNull
    String firstName;

    @NonNull
    String lastName;

    String username;

    String password;

    boolean isActive;
}