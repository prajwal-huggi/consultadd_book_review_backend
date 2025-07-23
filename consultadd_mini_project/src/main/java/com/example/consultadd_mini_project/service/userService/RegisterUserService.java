package com.example.consultadd_mini_project.service.userService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Pattern;

@Service
public class RegisterUserService {
    private final BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    @Autowired
    UserRepo repo;

    private Boolean isValidEmail(String email){
        if(email== null) return false;
        System.out.println("Email in registerUserService is "+ email);
        final String regex= "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return Pattern.matches(regex, email);
    }

    private Boolean isValidPassword(String password){
        if(password== null) return false;
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(regex, password);
    }

    public ResponseEntity<ResponseDTO<Object>> registerUser(@RequestBody User user){
        try{
            User findUser= repo.findByEmail(user.getEmail());

            //If user already exists
            if(findUser!= null){
                ResponseDTO<Object> response= new ResponseDTO<>(409, "Email already exist", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            //Checking if the email is valid or not
            if(!isValidEmail(user.getEmail())){
                ResponseDTO<Object> response= new ResponseDTO<>(400, "Email is not valid", null);

                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            //Checking if the password is valid or not
            if(!isValidPassword(user.getPassword())){
                ResponseDTO<Object> response= new ResponseDTO<>(400, "Password is invalid", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }
            user.setPassword(encoder.encode(user.getPassword()));
            User saveUser= repo.save(user);
            System.out.println("Registered user is "+ saveUser);
            ResponseDTO<Object> response= new ResponseDTO<>(200, "User Saved Successfully!", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<Object> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
