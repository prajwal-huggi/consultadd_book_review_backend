package com.example.consultadd_mini_project.service.authorService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.model.Author;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllAuthorService {
    @Autowired
    AuthorRepo authorRepo;
    public ResponseEntity<ResponseDTO<List<Author>>> getAllAuthor(){
        try{
            List<Author> allAuthors= authorRepo.findAll();

            ResponseDTO<List<Author>> response= new ResponseDTO<>(200, "Fetched all authors", allAuthors);

            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch(Exception e){
            ResponseDTO<List<Author>> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
