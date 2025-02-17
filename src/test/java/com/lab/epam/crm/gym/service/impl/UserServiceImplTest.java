package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.dto.UserResponseDto;
import com.lab.epam.crm.gym.entity.User;
import com.lab.epam.crm.gym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConversionService conversionService;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, conversionService);
    }

    @Test
    void createUser_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");

        User user = new User();
        User savedUser = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(conversionService.convert(any(User.class), eq(UserResponseDto.class))).thenReturn(userResponseDto);

        UserResponseDto result = userService.createUser(userRequestDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(conversionService, times(1)).convert(any(User.class), eq(UserResponseDto.class));
    }

    @Test
    void getUserByUsername_Success() {
        String username = "testUser";
        User user = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(conversionService.convert(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUserByUsername(username);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(username);
        verify(conversionService, times(1)).convert(user, UserResponseDto.class);
    }

    @Test
    void authenticate_Success() {
        String username = "testUser";
        String password = "password";
        User user = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));
        when(conversionService.convert(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userService.authenticate(username, password);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsernameAndPassword(username, password);
        verify(conversionService, times(1)).convert(user, UserResponseDto.class);
    }

    @Test
    void updateUserPassword_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("testUser");
        userRequestDto.setId(1);

        User user = new User();
        User updatedUser = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findById(userRequestDto.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(conversionService.convert(updatedUser, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userService.updateUserPassword(userRequestDto, "newPassword");

        assertNotNull(result);
        verify(userRepository, times(1)).findById(userRequestDto.getId());
        verify(userRepository, times(1)).save(user);
        verify(conversionService, times(1)).convert(updatedUser, UserResponseDto.class);
    }

    @Test
    void activateUser_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("testUser");

        User user = new User();
        user.setUsername("testUser");
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findByUsername(userRequestDto.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(conversionService.convert(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userService.activateUser(userRequestDto);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(userRequestDto.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(conversionService, times(1)).convert(user, UserResponseDto.class);
    }

    @Test
    void deactivateUser_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("testUser");

        User user = new User();
        user.setUsername("testUser");
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findByUsername(userRequestDto.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(conversionService.convert(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userService.deactivateUser(userRequestDto);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(userRequestDto.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(conversionService, times(1)).convert(user, UserResponseDto.class);
    }
}