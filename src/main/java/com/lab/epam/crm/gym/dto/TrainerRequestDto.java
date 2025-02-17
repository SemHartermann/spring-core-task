package com.lab.epam.crm.gym.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerRequestDto extends BaseDto {
    TrainingTypeDto specialization;
    UserRequestDto user;
}