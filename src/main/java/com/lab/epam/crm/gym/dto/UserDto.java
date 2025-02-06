package com.lab.epam.crm.gym.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public abstract class UserDto {
    Integer id;

    @NonNull
    String firstName;

    @NonNull
    String lastName;

    boolean isActive;
}