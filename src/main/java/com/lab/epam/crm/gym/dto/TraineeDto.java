package com.lab.epam.crm.gym.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class TraineeDto extends UserDto {
    String dateOfBirth;

    String address;
}