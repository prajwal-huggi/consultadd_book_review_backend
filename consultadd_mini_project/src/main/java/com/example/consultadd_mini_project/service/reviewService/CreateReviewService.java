package com.example.consultadd_mini_project.service.reviewService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.DTO.reviewDTO.ReviewRequestDTO;
import com.example.consultadd_mini_project.DTO.reviewDTO.ReviewResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.Repository.ReviewRepo;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Review;
import com.example.consultadd_mini_project.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CreateReviewService {
    @Autowired
    BookRepo bookRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ReviewRepo reviewRepo;

    public ResponseEntity<ResponseDTO<ReviewResponseDTO>> createReview(ReviewRequestDTO reviewRequestDTO){
        try{
            Optional<User> findUser= userRepo.findById(reviewRequestDTO.getUserId());
            if(findUser.isEmpty()) {
                ResponseDTO<ReviewResponseDTO> response = new ResponseDTO<>(404, "User not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            Optional<Book> findBook= bookRepo.findById(reviewRequestDTO.getBookId());
            if(findBook.isEmpty()){
                ResponseDTO<ReviewResponseDTO> response= new ResponseDTO<>(404, "Book not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            Review newReview= new Review();
            newReview.setBook(findBook.get());
            newReview.setUser(findUser.get());
            newReview.setContent(reviewRequestDTO.getContent());
            newReview.setValue(reviewRequestDTO.getValue());

            Review saveReview= reviewRepo.save(newReview);

            ReviewResponseDTO reviewResponseDTO= ReviewResponseDTO.builder()
                    .reviewId(saveReview.getId())
                    .reviewText(saveReview.getContent())
                    .userId(saveReview.getUser().getId())
                    .bookId(saveReview.getBook().getId())
                    .username(saveReview.getUser().getUsername())
                    .email(saveReview.getUser().getEmail())
                    .likes(saveReview.getLikes()!= null? saveReview.getLikes().size(): 0)
                    .dislikes(saveReview.getDislikes()!= null? saveReview.getDislikes().size(): 0)
                    .createdAt(saveReview.getCreatedAt())
                    .updatedAt(saveReview.getUpdatedAt())
                    .rating(saveReview.getValue())
                    .build();

            ResponseDTO<ReviewResponseDTO> response= new ResponseDTO<>(200, "Review saved in database", reviewResponseDTO);
            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch(Exception e){
            ResponseDTO<ReviewResponseDTO> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
