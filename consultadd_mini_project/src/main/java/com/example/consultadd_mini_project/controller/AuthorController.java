package com.example.consultadd_mini_project.controller;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.model.Author;
import com.example.consultadd_mini_project.service.authorService.CreateAuthorService;
import com.example.consultadd_mini_project.service.authorService.GetAllAuthorService;
import com.example.consultadd_mini_project.service.authorService.GetAuthorByEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class AuthorController {
    @Autowired
    CreateAuthorService createAuthorService;

    @Autowired
    GetAllAuthorService getAllAuthorService;

    @Autowired
    GetAuthorByEmailService getAuthorByEmailService;

    @PostMapping("/author/create")
    public ResponseEntity<ResponseDTO<Object>> createAuthor(
            @RequestParam("name") String name,
            @RequestParam("bio") String bio,
            @RequestParam("email") String email,
            @RequestParam("imageUrl")MultipartFile imageUrl
            ){
        return createAuthorService.createAuthor(name, bio, email, imageUrl);
    }

    @GetMapping("/author/getAll")
    public ResponseEntity<ResponseDTO<List<Author>>> getAllAuthor(){
        return getAllAuthorService.getAllAuthor();
    }

    @GetMapping("/author/getIdByEmail")
    public ResponseEntity<ResponseDTO<UUID>> getIdByEmail(@RequestParam("email") String email){
        return getAuthorByEmailService.getAuthorIdByEmail(email);
    }
}
