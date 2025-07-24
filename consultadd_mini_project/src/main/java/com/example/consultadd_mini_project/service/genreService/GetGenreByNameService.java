package com.example.consultadd_mini_project.service.genreService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.GenreRepo;
import com.example.consultadd_mini_project.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetGenreByNameService {

    @Autowired
    GenreRepo genreRepo;

    public ResponseEntity<ResponseDTO<UUID>> getGenreByName(String name){
        try{
            Optional<Genre> genreOptional= genreRepo.findByName(name);
            if (genreOptional.isPresent()) {
                UUID id = genreOptional.get().getId();
                ResponseDTO<UUID> response = new ResponseDTO<>(200, "Genre id is present", id);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            } else {
                ResponseDTO<UUID> response = new ResponseDTO<>(404, "Genre not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }
        }catch(Exception e){
            ResponseDTO<UUID> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
