package com.museri.challenge.service;

import com.museri.challenge.dto.UserLoggedResponse;
import com.museri.challenge.dto.UserSingUpRequest;
import com.museri.challenge.dto.UserSingUpResponse;

import java.util.Optional;

public interface UserService {
    Optional<UserSingUpResponse> save(UserSingUpRequest newUser);

    Optional<UserLoggedResponse> login(String email);
}
