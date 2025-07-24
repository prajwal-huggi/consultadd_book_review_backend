package com.example.consultadd_mini_project.service.genreService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.GenreRepo;
import com.example.consultadd_mini_project.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllGenreService {
    @Autowired
    GenreRepo repo;

    public ResponseEntity<ResponseDTO<List<Genre>>> getGenres(){
        try{
            List<Genre> allGenre= repo.findAll();

            ResponseDTO<List<Genre>> response= new ResponseDTO<>(200, "All genre fetched", allGenre);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<List<Genre>> response= new ResponseDTO<>(500, "Internal Server Error!", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
