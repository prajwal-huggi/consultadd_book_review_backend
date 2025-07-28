package com.example.consultadd_mini_project.controller;

import com.example.consultadd_mini_project.DTO.LikeAndDislikeRequestDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.DTO.reviewDTO.ReviewRequestDTO;
import com.example.consultadd_mini_project.DTO.reviewDTO.ReviewResponseDTO;
import com.example.consultadd_mini_project.service.reviewService.CreateDislikeService;
import com.example.consultadd_mini_project.service.reviewService.CreateLikeService;
import com.example.consultadd_mini_project.service.reviewService.CreateReviewService;
import com.example.consultadd_mini_project.service.reviewService.GetReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    CreateReviewService createReviewService;

    @Autowired
    GetReviewService getReviewService;

    @Autowired
    CreateLikeService createLikeService;

    @Autowired
    CreateDislikeService createDislikeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ReviewResponseDTO>> createReview(@RequestBody ReviewRequestDTO review){
        return createReviewService.createReview(review);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO<List<ReviewResponseDTO>>> getAllResponse(@RequestParam("id") UUID bookId){
        return getReviewService.getReview(bookId);
    }

    @PostMapping("/like")
    public ResponseEntity<ResponseDTO<Object>> createLike(@RequestBody LikeAndDislikeRequestDTO likeAndDislikeRequestDTO){
        return createLikeService.createLike(likeAndDislikeRequestDTO);
    }

    @PostMapping("/dislike")
    public ResponseEntity<ResponseDTO<Object>> createDislike(@RequestBody LikeAndDislikeRequestDTO dislikeAndDislikeRequestDTO){
        return createDislikeService.createDislike(dislikeAndDislikeRequestDTO);
    }
}
