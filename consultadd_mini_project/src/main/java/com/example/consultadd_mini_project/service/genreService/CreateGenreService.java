package com.example.consultadd_mini_project.service.genreService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.GenreRepo;
import com.example.consultadd_mini_project.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateGenreService {
    @Autowired
    GenreRepo repo;

    public ResponseEntity<ResponseDTO<Object>> saveGenre(Genre genre){
        try{
            repo.save(genre);
            ResponseDTO<Object> response= new ResponseDTO<>(200, "Saved genre in genre table", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<Object> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
