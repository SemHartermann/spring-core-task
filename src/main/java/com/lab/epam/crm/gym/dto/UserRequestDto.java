package com.lab.epam.crm.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto extends BaseDto {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    String username;
    String password;
    Boolean isActive;
}