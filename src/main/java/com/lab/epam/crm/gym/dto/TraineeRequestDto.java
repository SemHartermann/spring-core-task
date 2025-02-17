package com.lab.epam.crm.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineeRequestDto extends BaseDto {
    Date dateOfBirth;
    String address;
    @NotNull
    UserRequestDto user;
}