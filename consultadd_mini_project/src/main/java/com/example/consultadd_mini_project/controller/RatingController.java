package com.example.consultadd_mini_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.consultadd_mini_project.DTO.RatingDTO.RatingRequestDTO;
import com.example.consultadd_mini_project.DTO.RatingDTO.RatingResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.service.ratingService.CreateRatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    CreateRatingService createRatingService;

    @PostMapping("/create")
    ResponseEntity<ResponseDTO<RatingResponseDTO>> createRating(@RequestBody RatingRequestDTO rating){
        return createRatingService.createRating(rating);
    }
}
