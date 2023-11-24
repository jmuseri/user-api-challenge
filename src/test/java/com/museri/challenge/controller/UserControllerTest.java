package com.museri.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.museri.challenge.dto.UserLoggedResponse;
import com.museri.challenge.dto.UserSingUpRequest;
import com.museri.challenge.dto.UserSingUpResponse;
import com.museri.challenge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Mock
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testSaveUser() throws Exception {
        UserSingUpResponse userSingUpResponse = UserSingUpResponse.builder()
                .withId(1L)
                .withCreated(LocalDateTime.now())
                .withLastLogin(LocalDateTime.now())
                .withToken("TOKEN")
                .withIsActive(true)
                .build();

        Mockito.when(userService.save(any(UserSingUpRequest.class)))
                .thenReturn(Optional.of(userSingUpResponse));

        UserSingUpRequest request = new UserSingUpRequest();
        request.setEmail("test@example.com");
        request.setName("Test User");
        request.setPassword("Password22");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    void testLogin() throws Exception {
        UserLoggedResponse userLoggedResponse = UserLoggedResponse.builder()
                .withId(1L)
                .withCreated(LocalDateTime.now())
                .withLastLogin(LocalDateTime.now())
                .withToken("TOKEN")
                .withIsActive(true)
                .withName("AA")
                .withEmail("aa@bb.cc")
                .withPassword("ENCPASS")
                .withPhones(new ArrayList<>())
                .build();

        Mockito.when(userService.login(anyString()))
                .thenReturn(Optional.of(userLoggedResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/login")
                        .param("token", "test-token"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(print());
    }
}