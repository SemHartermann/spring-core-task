package com.lab.epam.crm.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerRequestDto extends BaseDto {
    @NotNull
    TrainingTypeDto specialization;
    @NotNull
    UserRequestDto user;
}