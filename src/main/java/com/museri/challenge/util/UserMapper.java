package com.museri.challenge.util;

import com.museri.challenge.dto.UserSingUpRequest;
import com.museri.challenge.model.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static User buildUser(UserSingUpRequest userSignUpRequest) {
        User user = new User();
        user.setName(userSignUpRequest.getName());
        user.setIsActive(true);
        user.setEmail(userSignUpRequest.getEmail());
        user.setPhones(userSignUpRequest.getPhones().stream()
                .map(PhoneMapper::mapPhone)
                .collect(Collectors.toList())
        );
        return user;
    }
}