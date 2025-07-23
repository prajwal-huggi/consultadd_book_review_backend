package com.example.consultadd_mini_project.service;

import com.example.consultadd_mini_project.DTO.LoginResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    UserRepo repo;

    @Autowired
    JwtService jwtService;

    public ResponseEntity<ResponseDTO<LoginResponseDTO>> loginUser(User user){
        try{
            Authentication authentication= authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if(authentication.isAuthenticated()){
                String generateToken= jwtService.generateToken(user.getEmail());

                LoginResponseDTO loginResponse= new LoginResponseDTO(generateToken, user.getEmail(),repo.findByEmail(user.getEmail()).getRole());
                ResponseDTO<LoginResponseDTO> response= new ResponseDTO<>(200, "User Logged in!", loginResponse);

                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            //Impossible to move here
            ResponseDTO<LoginResponseDTO> responseDTO= new ResponseDTO<>(401, "Login Unsuccessful",null);
            return ResponseEntity.status(responseDTO.getStatus_code()).body(responseDTO);
        }
        catch (BadCredentialsException ex) {
            ResponseDTO<LoginResponseDTO> responseDTO = new ResponseDTO<>(401, "Invalid Credentials "+ex.getMessage(), null);
            return ResponseEntity.status(responseDTO.getStatus_code()).body(responseDTO);
        }
        catch(Exception e){
            ResponseDTO<LoginResponseDTO> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
