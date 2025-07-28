package com.example.consultadd_mini_project.service.authorService;

import com.example.consultadd_mini_project.DTO.AuthorResponseDTO;
import com.example.consultadd_mini_project.DTO.BookResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.model.Author;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GetAllAuthorService {
    @Autowired
    AuthorRepo authorRepo;
    public ResponseEntity<ResponseDTO<List<AuthorResponseDTO>>> getAllAuthor(){
        try{
            List<Author> allAuthors= authorRepo.findAll();

            List<AuthorResponseDTO> authorResponse= allAuthors.stream().map(author->{
                return AuthorResponseDTO.builder()
                        .id(author.getId())
                        .email(author.getEmail())
                        .bio(author.getBio())
                        .name(author.getName())
                        .imageURL(author.getImageURL())
                        .books(author.getBooks().stream()
                                .map(book-> BookResponseDTO.builder()
                                        .id(book.getId())
                                        .title(book.getTitle())
                                        .build())
                                .collect(Collectors.toList()))
                        .build();
            }).collect(Collectors.toList());

            ResponseDTO<List<AuthorResponseDTO>> response= new ResponseDTO<>(200, "Fetched all authors", authorResponse);
            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch(Exception e){
            ResponseDTO<List<AuthorResponseDTO>> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
