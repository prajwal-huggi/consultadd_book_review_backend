package com.example.consultadd_mini_project.service.reviewService;

import com.example.consultadd_mini_project.DTO.LikeAndDislikeRequestDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.ReviewRepo;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.Review;
import com.example.consultadd_mini_project.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CreateDislikeService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReviewRepo reviewRepo;
    public ResponseEntity<ResponseDTO<Object>> createDislike(LikeAndDislikeRequestDTO dislikeRequestDTO){
        try{
            Optional<User> findUserOptional= userRepo.findById(dislikeRequestDTO.getUserId());
            if(findUserOptional.isEmpty()){
                ResponseDTO<Object> response= new ResponseDTO<>(404, "User not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }
            User findUser= findUserOptional.get();

            Optional<Review> findReviewOptional= reviewRepo.findById(dislikeRequestDTO.getReviewId());
            if(findReviewOptional.isEmpty()){
                ResponseDTO<Object> response= new ResponseDTO<>(404, "Review not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }
            Review findReview= findReviewOptional.get();

            if(findReview.getDislikes().contains(findUser)){
                ResponseDTO<Object> response= new ResponseDTO<>(200, "User already disliked", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            findReview.getLikes().remove(findUser);
            findReview.getDislikes().add(findUser);
            reviewRepo.save(findReview);

            ResponseDTO<Object> response= new ResponseDTO<>(200, "User disliked the post", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch(Exception e){
            ResponseDTO<Object> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
