package com.example.consultadd_mini_project.service.userService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LogoutUserService {
    public ResponseEntity<ResponseDTO<String>> logoutUser(HttpServletRequest http){
        try{
            HttpSession session= http.getSession(false);

            if(session!= null){
                session.invalidate();
            }

            SecurityContextHolder.clearContext();

            ResponseDTO<String> response= new ResponseDTO<>(200, "Logout Successful", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            e.printStackTrace();
            ResponseDTO<String> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
