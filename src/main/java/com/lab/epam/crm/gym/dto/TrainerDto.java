package com.lab.epam.crm.gym.dto;

import com.lab.epam.crm.gym.entity.TrainingType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class TrainerDto extends UserDto {
    TrainingType specialization;
}