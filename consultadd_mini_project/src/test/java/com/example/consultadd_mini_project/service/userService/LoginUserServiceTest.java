package com.example.consultadd_mini_project.service.userService;

import com.example.consultadd_mini_project.DTO.LoginResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.User;
import com.example.consultadd_mini_project.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUserServiceTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserRepo userRepo;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginUserService loginUserService;

    @Test
    void testLoginUser_Success() {
        // Arrange
        String email = "a@gmail.com";
        String password = "Abhishek@1234";
        UUID userId = UUID.randomUUID();
        String jwtToken = "mocked-jwt-token";

        User inputUser = new User();
        inputUser.setEmail(email);
        inputUser.setPassword(password);

        User storedUser = new User();
        storedUser.setId(userId);
        storedUser.setEmail(email);
        storedUser.setRole(User.Roles.USER);

        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(email)).thenReturn(jwtToken);
        when(userRepo.findByEmail(email)).thenReturn(storedUser);

        // Act
        ResponseEntity<ResponseDTO<LoginResponseDTO>> response = loginUserService.loginUser(inputUser);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getData().getId()); // Use getUserId(), not getId()
        assertEquals(jwtToken, response.getBody().getData().getToken());
        assertEquals(email, response.getBody().getData().getEmail());
        assertEquals(User.Roles.USER, response.getBody().getData().getRole());

        // Verify interactions
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(email);
        verify(userRepo, times(2)).findByEmail(email);
    }
}
