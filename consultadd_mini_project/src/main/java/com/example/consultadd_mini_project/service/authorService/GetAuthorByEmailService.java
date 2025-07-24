package com.example.consultadd_mini_project.service.authorService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.model.Author;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class GetAuthorByEmailService {
    @Autowired
    AuthorRepo authorRepo;

    public ResponseEntity<ResponseDTO<UUID>> getAuthorIdByEmail(String email){
        try{
            Optional<Author> author= authorRepo.findByEmail(email);
            if(author.isEmpty()){
                ResponseDTO<UUID> response= new ResponseDTO<>(404, "Author is not present", null);

                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            UUID id= author.get().getId();
            ResponseDTO<UUID> response= new ResponseDTO<>(200, "Id of the author is fetched", id);
            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch(Exception e){
            System.out.println("Exception is "+ e);
            ResponseDTO<UUID> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
