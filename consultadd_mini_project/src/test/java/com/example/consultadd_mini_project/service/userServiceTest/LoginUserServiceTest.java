package com.example.consultadd_mini_project.service.userServiceTest;

import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.service.JwtService;
import com.example.consultadd_mini_project.service.userService.LoginUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class LoginUserServiceTest {
    private AuthenticationManager authManager;
    private UserRepo userRepo;
    private JwtService jwtService;
    private LoginUserService loginUserService;

    @BeforeEach
    void setUp(){
        authManager= mock(AuthenticationManager.class);
        userRepo= mock(UserRepo.class);
        jwtService= mock(JwtService.class);
        loginUserService= mock(LoginUserService.class);
    }

    @Test
    void testLoginUser_Success(){
        String email= "a@gmail.com";
        String password="Abhishek@1234";
        UUID userId= UUID.randomUUID();
        String jwtToken= "mock-jwt-token";
    }

}
