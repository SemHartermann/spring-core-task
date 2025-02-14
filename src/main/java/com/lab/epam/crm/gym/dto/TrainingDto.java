package com.lab.epam.crm.gym.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingDto extends BaseDto {
    TraineeDto trainee;
    TrainerDto trainer;
    String trainingName;
    TrainingTypeDto trainingType;
    Date trainingDate;
    Integer trainingDuration;
}