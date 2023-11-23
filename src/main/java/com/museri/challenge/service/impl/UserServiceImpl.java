package com.museri.challenge.service.impl;


import com.museri.challenge.dto.PhoneDTO;
import com.museri.challenge.dto.UserLoggedResponse;
import com.museri.challenge.dto.UserSingUpRequest;
import com.museri.challenge.dto.UserSingUpResponse;
import com.museri.challenge.exception.BadRequestException;
import com.museri.challenge.exception.EntityNotFoundException;
import com.museri.challenge.exception.UniqueEmailException;
import com.museri.challenge.model.User;
import com.museri.challenge.repository.UserRepository;
import com.museri.challenge.service.UserService;
import com.museri.challenge.util.JwtToken;
import com.museri.challenge.util.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtToken jwtToken;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtToken jwtToken) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtToken = jwtToken;
    }

    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<UserSingUpResponse> save(UserSingUpRequest userSingUpRequest) {
        userRepository.findByEmail(userSingUpRequest.getEmail())
                .ifPresent(user -> {
                    throw new UniqueEmailException("The email [" + user.getEmail() + "] is already registered");
                });
        User newUser = UserMapper.buildUser(userSingUpRequest);
        newUser.setToken(jwtToken.generateToken(newUser.getEmail(), newUser.getLastLogin()));
        newUser.setPassword(bCryptPasswordEncoder.encode(userSingUpRequest.getPassword()));
        newUser = userRepository.save(newUser);
        return Optional.of(UserSingUpResponse.builder()
                .withId(newUser.getId())
                .withCreated(newUser.getCreated())
                .withLastLogin(LocalDateTime.now())
                .withToken(newUser.getToken())
                .withIsActive(newUser.getIsActive())
                .build());
    }

    @Override
    public Optional<UserLoggedResponse> login(String token) {

        String email;
        try {
            email = jwtToken.getEmailFromToken(token);
        } catch (Exception e) {
            throw new BadRequestException("Invalid Token.");
        }
        User userLoggedIn = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("There is no user with the email: " + email));
        userLoggedIn.setLastLogin(LocalDateTime.now());
        userLoggedIn.setToken(jwtToken.generateToken(userLoggedIn.getEmail(), userLoggedIn.getLastLogin()));
        return save(userLoggedIn)
                .map(userUpdate -> {
                    List<PhoneDTO> phoneDtos = userUpdate.getPhones()
                            .stream()
                            .map(phone -> new PhoneDTO(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                            .collect(Collectors.toList());

                    return UserLoggedResponse.builder()
                            .withId(userUpdate.getId())
                            .withCreated(userUpdate.getCreated())
                            .withLastLogin(LocalDateTime.now())
                            .withToken(userUpdate.getToken())
                            .withIsActive(userUpdate.getIsActive())
                            .withName(userUpdate.getName())
                            .withEmail(userUpdate.getEmail())
                            .withPassword(userUpdate.getPassword())
                            .withPhones(phoneDtos)
                            .build();
                });
    }

}
