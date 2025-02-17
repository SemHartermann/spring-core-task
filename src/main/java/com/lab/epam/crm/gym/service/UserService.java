package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto getUserById(Integer id);

    UserResponseDto authenticate(String username, String password);

    void checkIsActive(UserRequestDto userRequestDto);

    UserResponseDto updateUserPassword(UserRequestDto userRequestDto, String newPassword);

    UserResponseDto activateUser(UserRequestDto userRequestDto);

    UserResponseDto deactivateUser(UserRequestDto userRequestDto);
}