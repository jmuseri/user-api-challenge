package com.museri.challenge.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenTest {

    @Mock
    private Claims mockClaims;

    @InjectMocks
    private JwtToken jwtToken;

    @Test
    void testGenerateToken() {

        String email = "test@example.com";
        LocalDateTime lastLogin = LocalDateTime.now();
        
        String token = jwtToken.generateToken(email, lastLogin);

        assertNotNull(token);
    }

    @Test
    void testGetClaimsFromToken() throws Exception {
        String token = "test-token";
        Mockito.when(mockClaims.getExpiration()).thenReturn(new Date());
        Mockito.when(jwtToken.getClaimsFromToken(token)).thenReturn(mockClaims);

        Claims result = jwtToken.getClaimsFromToken(token);

        assertNotNull(result);
    }

    @Test
    void testGetEmailFromToken() throws Exception {
        String token = "test-token";
        Mockito.when(mockClaims.get("email")).thenReturn("test@example.com");
        Mockito.when(jwtToken.getClaimsFromToken(token)).thenReturn(mockClaims);

        String result = jwtToken.getEmailFromToken(token);

        assertEquals("test@example.com", result);
    }

    @Test
    void testIsExpiredToken() throws Exception {
        String token = "test-token";
        Date expiration = Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC));
        Mockito.when(mockClaims.getExpiration()).thenReturn(expiration);
        Mockito.when(jwtToken.getClaimsFromToken(token)).thenReturn(mockClaims);

        boolean result = jwtToken.isExpiredToken(token);

        assertFalse(result);
    }
}
