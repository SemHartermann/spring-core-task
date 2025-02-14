package com.lab.epam.crm.gym.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerDto extends BaseDto {
    TrainingTypeDto specialization;
    UserDto user;
}