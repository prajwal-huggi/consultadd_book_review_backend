package com.example.consultadd_mini_project.controller;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.model.Genre;
import com.example.consultadd_mini_project.service.genreService.CreateGenreService;
import com.example.consultadd_mini_project.service.genreService.GetAllGenreService;
import com.example.consultadd_mini_project.service.genreService.GetGenreByNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class GenreController {
    @Autowired
    CreateGenreService createGenreService;

    @Autowired
    GetAllGenreService getAllGenreService;

    @Autowired
    GetGenreByNameService getGenreByNameService;

    @PostMapping("/genre/create")
    public ResponseEntity<ResponseDTO<Object>>  createGenre(@RequestBody Genre genre){
        return createGenreService.saveGenre(genre);
    }

    @GetMapping("/genre/getAll")
    public ResponseEntity<ResponseDTO<List<Genre>>> getAllGenre(){
        return getAllGenreService.getGenres();
    }

    @GetMapping("/genre/getIdByName")
    public ResponseEntity<ResponseDTO<UUID>> getIdByName(@RequestParam("name") String name){
        return getGenreByNameService.getGenreByName(name);
    }
}
