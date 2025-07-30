package com.example.consultadd_mini_project.service.userService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private RegisterUserService registerUserService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setEmail("valid@example.com");
        validUser.setPassword("Strong@123");
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepo.findByEmail(validUser.getEmail())).thenReturn(null);
        when(userRepo.save(any(User.class))).thenReturn(validUser);

        ResponseEntity<ResponseDTO<Object>> response = registerUserService.registerUser(validUser);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User Saved Successfully!", response.getBody().getMessage());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        when(userRepo.findByEmail(validUser.getEmail())).thenReturn(new User());

        ResponseEntity<ResponseDTO<Object>> response = registerUserService.registerUser(validUser);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Email already exist", response.getBody().getMessage());
        verify(userRepo, never()).save(any());
    }

    @Test
    void testRegisterUser_InvalidEmail() {
        validUser.setEmail("invalid-email");

        ResponseEntity<ResponseDTO<Object>> response = registerUserService.registerUser(validUser);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Email is not valid", response.getBody().getMessage());
        verify(userRepo, never()).save(any());
    }

    @Test
    void testRegisterUser_InvalidPassword() {
        validUser.setPassword("weak"); // too short and no special char

        ResponseEntity<ResponseDTO<Object>> response = registerUserService.registerUser(validUser);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Password is invalid", response.getBody().getMessage());
        verify(userRepo, never()).save(any());
    }

    @Test
    void testRegisterUser_InternalServerError() {
        when(userRepo.findByEmail(validUser.getEmail())).thenReturn(null);
        when(userRepo.save(any(User.class))).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<ResponseDTO<Object>> response = registerUserService.registerUser(validUser);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal Server Error", response.getBody().getMessage());
    }
}
