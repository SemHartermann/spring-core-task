package com.lab.epam.crm.gym.service.impl;

import com.lab.epam.crm.gym.dto.UserRequestDto;
import com.lab.epam.crm.gym.entity.User;
import com.lab.epam.crm.gym.repository.UserRepository;
import com.lab.epam.crm.gym.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    ConversionService conversionService;

    @Transactional
    @Override
    public UserRequestDto createUser(UserRequestDto userRequestDto) {
        log.trace("Creating user with first name: {} and last name: {}", userRequestDto.getFirstName(), userRequestDto.getLastName());

        String username = generateUsername(userRequestDto);
        String password = generatePassword();

        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setUsername(username);
        user.setPassword(password);
        user.setIsActive(false);

        User savedUser = userRepository.save(user);

        log.debug("User created with username: {}", username);

        return conversionService.convert(savedUser, UserRequestDto.class);
    }

    private String generateUsername(UserRequestDto userRequestDto) {
        log.debug("Generating username for user profile: {}", userRequestDto);

        String baseUsername = userRequestDto.getFirstName() + "." + userRequestDto.getLastName();
        int serialNumber = 0;
        String newUsername = baseUsername;

        List<User> allUsers = userRepository.findAll();

        String finalNewUsername = newUsername;
        while (allUsers.stream().anyMatch(u -> u.getUsername().equals(finalNewUsername))) {
            serialNumber++;
            newUsername = baseUsername + serialNumber;
        }

        return newUsername;
    }

    private String generatePassword() {
        return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    @Override
    public UserRequestDto getUserByUsername(String username) {
        log.trace("Fetching user by username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return conversionService.convert(user, UserRequestDto.class);
    }

    @Override
    public UserRequestDto getUserById(Integer id) {
        log.trace("Fetching user by id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return conversionService.convert(user, UserRequestDto.class);
    }

    @Override
    public UserRequestDto authenticate(String username, String password) {
        log.trace("Authenticating user with username: {}", username);

        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isPresent()) {
            User u = user.get();
            u.setIsActive(true);
            userRepository.save(u);

            log.debug("User authenticated and activated with username: {}", username);

            return conversionService.convert(u, UserRequestDto.class);
        }

        log.warn("Authentication failed for username: {}", username);

        return null;
    }

    @Override
    public void checkIsActive(UserRequestDto userRequestDto) {
        User user = userRepository.findById(userRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userRequestDto.getId() + " not found"));

        if (!user.getIsActive()) {
            throw new IllegalStateException("User is not authenticated");
        }
    }

    @Override
    public UserRequestDto updateUserPassword(UserRequestDto userRequestDto, String newPassword) {
        checkIsActive(userRequestDto);

        log.trace("Updating password for user: {}", userRequestDto.getUsername());

        User user = conversionService.convert(userRequestDto, User.class);
        Objects.requireNonNull(user).setPassword(newPassword);
        User updatedUser = userRepository.save(user);

        log.debug("Password updated for user: {}", user.getUsername());

        return conversionService.convert(updatedUser, UserRequestDto.class);
    }

    @Override
    public UserRequestDto activateUser(UserRequestDto userRequestDto) {
        log.trace("Activating user: {}", userRequestDto.getUsername());

        User user = userRepository.findByUsername(userRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: "
                        + userRequestDto.getUsername()));
        user.setIsActive(true);
        User updatedUser = userRepository.save(user);

        log.debug("User activated: {}", userRequestDto.getUsername());

        return conversionService.convert(updatedUser, UserRequestDto.class);
    }

    @Override
    public UserRequestDto deactivateUser(UserRequestDto userRequestDto) {
        log.trace("Deactivating user: {}", userRequestDto.getUsername());

        User user = userRepository.findByUsername(userRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setIsActive(false);
        User updatedUser = userRepository.save(user);

        log.debug("User deactivated: {}", userRequestDto.getUsername());

        return conversionService.convert(updatedUser, UserRequestDto.class);
    }
}