package com.example.consultadd_mini_project.DTO;

import com.example.consultadd_mini_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private UUID id;
    private String token;
    private String email;
    private User.Roles role;
}
