package com.lab.epam.crm.gym.service;

import com.lab.epam.crm.gym.dto.UserRequestDto;

public interface UserService {
    UserRequestDto createUser(UserRequestDto userRequestDto);

    UserRequestDto getUserByUsername(String username);

    UserRequestDto getUserById(Integer id);

    UserRequestDto authenticate(String username, String password);

    void checkIsActive(UserRequestDto userRequestDto);

    UserRequestDto updateUserPassword(UserRequestDto userRequestDto, String newPassword);

    UserRequestDto activateUser(UserRequestDto userRequestDto);

    UserRequestDto deactivateUser(UserRequestDto userRequestDto);
}