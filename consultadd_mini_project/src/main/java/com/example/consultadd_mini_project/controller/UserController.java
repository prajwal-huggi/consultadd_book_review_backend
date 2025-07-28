package com.example.consultadd_mini_project.controller;

import com.example.consultadd_mini_project.DTO.LoginResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.model.User;
import com.example.consultadd_mini_project.service.userService.LoginUserService;
import com.example.consultadd_mini_project.service.userService.LogoutUserService;
import com.example.consultadd_mini_project.service.userService.RegisterUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    RegisterUserService registerUserService;

    @Autowired
    LoginUserService loginUserService;

    @Autowired
    LogoutUserService logoutUserService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> getLogin(@RequestBody User user){
        return loginUserService.loginUser(user);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<Object>> registerUser(@RequestBody User user){
        return registerUserService.registerUser(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<String>> logout(HttpServletRequest http){
        return logoutUserService.logoutUser(http);
    }
}
