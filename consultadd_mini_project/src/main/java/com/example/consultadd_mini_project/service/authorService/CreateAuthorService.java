package com.example.consultadd_mini_project.service.authorService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.model.Author;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.regex.Pattern;

@Transactional
@Service
public class CreateAuthorService {

    @Autowired
    AuthorRepo authorRepo;

    private Boolean isValidEmail(String email){
        if(email== null) return false;
        System.out.println("Email in registerUserService is "+ email);
        final String regex= "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return Pattern.matches(regex, email);
    }
    public ResponseEntity<ResponseDTO<Object>> createAuthor(String name, String bio, String email, MultipartFile imageUrl){
        try{
            //Checking if the email is valid or not
            if (!isValidEmail(email)) {
                ResponseDTO<Object> response= new ResponseDTO<>(400, "Email id is not valid", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            //Checking if email is unique
            Optional<Author> findAuthor= authorRepo.findByEmail(email);
            if(findAuthor.isPresent()){
                ResponseDTO<Object> response= new ResponseDTO<>(409, "Email id already exists", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            Author author= new Author();
            byte[] imageByte= imageUrl.getBytes();

            author.setName(name);
            author.setBio(bio);
            author.setEmail(email);
            author.setImageURL(imageByte);

            authorRepo.save(author);

            ResponseDTO<Object> response= new ResponseDTO<>(200, "Author saved in the database", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch(Exception e){
            e.printStackTrace();
            ResponseDTO<Object> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
