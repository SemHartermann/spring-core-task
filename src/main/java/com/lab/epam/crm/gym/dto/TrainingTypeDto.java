package com.lab.epam.crm.gym.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingTypeDto extends BaseDto {
    String trainingTypeName;
}