package com.lab.epam.crm.gym.dto;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public abstract class UserDto {
    Integer id;

    String firstName;

    String lastName;

    String username;

    String password;

    boolean isActive;
}