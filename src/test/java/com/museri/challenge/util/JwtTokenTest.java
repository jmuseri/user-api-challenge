package com.museri.challenge.util;

import com.museri.challenge.config.JacksonConfig;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class})
@Import ({JacksonConfig.class})
public class JwtTokenTest {

    @InjectMocks
    private JwtToken jwtToken= new JwtToken("globalLogic", 90, "1234567890", "MYSecretKeyMYSecretKeyMYSecretKeyMYSecretKeyMYSecretKey");

    @Test
    void testGenerateToken() {
        String email = "test@example.com";
        LocalDateTime lastLogin = LocalDateTime.now();
        String token = jwtToken.generateToken(email, lastLogin);
        assertNotNull(token);
    }

    @Test
    void testGetClaimsFromToken() throws Exception {
        String email = "test@example.com";
        LocalDateTime lastLogin = LocalDateTime.now();
        String token = jwtToken.generateToken(email, lastLogin);
        Claims result = jwtToken.getClaimsFromToken(token);
        assertNotNull(result);
    }

    @Test
    void testGetEmailFromToken() throws Exception {
        String email = "test@example.com";
        LocalDateTime lastLogin = LocalDateTime.now();
        String token = jwtToken.generateToken(email, lastLogin);
        String result = jwtToken.getEmailFromToken(token);
        assertEquals("test@example.com", result);
    }

    @Test
    void testIsExpiredToken() throws Exception {
        String email = "test@example.com";
        LocalDateTime lastLogin = LocalDateTime.now();
        String token = jwtToken.generateToken(email, lastLogin);
        boolean result = jwtToken.isExpiredToken(token);
        assertFalse(result);
    }
}
