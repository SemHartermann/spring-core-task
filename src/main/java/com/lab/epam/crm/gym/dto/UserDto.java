package com.lab.epam.crm.gym.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto extends BaseDto {
    String firstName;
    String lastName;
    String username;
    String password;
    Boolean isActive;
}