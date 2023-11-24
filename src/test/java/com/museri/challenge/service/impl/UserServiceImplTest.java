package com.museri.challenge.service.impl;

import com.museri.challenge.dto.UserLoggedResponse;
import com.museri.challenge.dto.UserSingUpRequest;
import com.museri.challenge.dto.UserSingUpResponse;
import com.museri.challenge.exception.EntityNotFoundException;
import com.museri.challenge.exception.UniqueEmailException;
import com.museri.challenge.model.User;
import com.museri.challenge.repository.UserRepository;
import com.museri.challenge.util.JwtToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtToken jwtToken;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSave_UniqueEmailException() {
        UserSingUpRequest userSingUpRequest = new UserSingUpRequest();
        userSingUpRequest.setEmail("existing-email@example.com");
        Mockito.when(userRepository.findByEmail(userSingUpRequest.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(UniqueEmailException.class, () -> userService.save(userSingUpRequest));
    }

    @Test
    void testSave_Success() {
        UserSingUpRequest userSingUpRequest = new UserSingUpRequest();
        userSingUpRequest.setEmail("new-email@example.com");

        Mockito.when(userRepository.findByEmail(userSingUpRequest.getEmail())).thenReturn(Optional.empty());
        Mockito.when(jwtToken.generateToken(any(), any())).thenReturn("dummy-token");
        Mockito.when(bCryptPasswordEncoder.encode(any())).thenReturn("hashed-password");

        User user = new User();
        user.setEmail(userSingUpRequest.getEmail());
        user.setToken("dummy-token");
        user.setIsActive(true);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<UserSingUpResponse> result = userService.save(userSingUpRequest);
        assertTrue(result.isPresent());
        UserSingUpResponse userResponse = result.get();
        assertEquals("dummy-token", userResponse.getToken());
        assertTrue(userResponse.getIsActive());
    }

    @Test
    void testSave_FailToSave() {
        UserSingUpRequest userSingUpRequest = new UserSingUpRequest();
        userSingUpRequest.setEmail("new-email@example.com");
        Mockito.when(userRepository.findByEmail(userSingUpRequest.getEmail())).thenReturn(Optional.empty());
        Mockito.when(jwtToken.generateToken(any(), any())).thenReturn("dummy-token");
        Mockito.when(bCryptPasswordEncoder.encode(any())).thenReturn("hashed-password");

        Mockito.when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Failed to save"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.save(userSingUpRequest));
        assertEquals("Failed to save", exception.getMessage());
    }


    @Test
    void testLogin_UserDoesNotExist() {
        String token = "MYTOKEN";
        Mockito.when(jwtToken.getEmailFromToken(any())).thenReturn("jj@mm.ss");
        RuntimeException exception = assertThrows(EntityNotFoundException.class, () -> userService.login(token));
        assertEquals("There is no user with the email: jj@mm.ss", exception.getMessage());
    }
    @Test
    void testLogin_Success() {

        User user = new User();
        user.setEmail("jmuseri@hotmail.com");
        user.setToken("dummy-token");
        user.setIsActive(true);

        Mockito.when(jwtToken.getEmailFromToken(any())).thenReturn("jmuseri@hotmail.com");
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Mockito.when(jwtToken.generateToken(any(), any())).thenReturn("new-token");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);


        Optional<UserLoggedResponse> result = userService.login("dummy-token");
        assertTrue(result.isPresent());
        UserLoggedResponse userResponse = result.get();
        assertEquals("new-token", userResponse.getToken());
        assertTrue(userResponse.getEmail().equals(user.getEmail()));

    }


}
