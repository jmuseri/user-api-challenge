package com.museri.challenge.controller;

import com.museri.challenge.dto.UserLoggedResponse;
import com.museri.challenge.dto.UserSingUpRequest;
import com.museri.challenge.dto.UserSingUpResponse;
import com.museri.challenge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping ("/api/users")
@RequiredArgsConstructor
@Api (tags = "User API", description = "User creation and query API")
public class UserController {

    private final UserService userService;


    @ApiOperation (value = "Create a new user", response = UserSingUpResponse.class)
    @PostMapping ("/sign-up")
    public ResponseEntity<UserSingUpResponse> saveUser(@Valid @RequestBody UserSingUpRequest newUser) {
        log.info("Save user: {}", newUser);
        return new ResponseEntity<>(userService.save(newUser).get(), HttpStatus.CREATED);
    }

    @ApiOperation (value = "Check for existing user", response = UserLoggedResponse.class)
    @GetMapping ("/login")
    public ResponseEntity<UserLoggedResponse> login(@RequestParam String token) throws Exception {
        log.info("Login user with Token: " + token);
        return ResponseEntity.accepted().body(userService.login(token).get());
    }
}
