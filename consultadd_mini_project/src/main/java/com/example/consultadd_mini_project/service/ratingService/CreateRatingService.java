package com.example.consultadd_mini_project.service.ratingService;

import com.example.consultadd_mini_project.DTO.ratingDTO.RatingRequestDTO;
import com.example.consultadd_mini_project.DTO.ratingDTO.RatingResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.Repository.RatingRepo;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Rating;
import com.example.consultadd_mini_project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateRatingService {
    @Autowired
    BookRepo bookRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RatingRepo ratingRepo;

    public ResponseEntity<ResponseDTO<RatingResponseDTO>> createRating(RatingRequestDTO dto){
       try {
           Optional<User> user = userRepo.findById(dto.getUserId());
           if(user.isEmpty()){
               ResponseDTO<RatingResponseDTO> responseDTO= new ResponseDTO<>(404, "User not found", null);
               return ResponseEntity.status(responseDTO.getStatus_code()).body(responseDTO);
           }

           Optional<Book> book = bookRepo.findById(dto.getBookId());
           if(book.isEmpty()){
               ResponseDTO<RatingResponseDTO> responseDTO= new ResponseDTO<>(404, "Book not found", null);
               return ResponseEntity.status(responseDTO.getStatus_code()).body(responseDTO);
           }

           Rating rating = new Rating();
           rating.setValue(dto.getValue());
           rating.setUser(user.get());
           rating.setBook(book.get());

           Rating saveRating = ratingRepo.save(rating);
           RatingResponseDTO ratingDTO = RatingResponseDTO.builder()
                   .id(saveRating.getId())
                   .value(saveRating.getValue())
                   .userId(saveRating.getUser().getId())
                   .bookId(saveRating.getBook().getId())
                   .build();


           ResponseDTO<RatingResponseDTO> response = new ResponseDTO<>(200, "Rating saved in database", ratingDTO);

           return ResponseEntity.status(response.getStatus_code()).body(response);
       }catch(Exception e){
           e.printStackTrace();
           ResponseDTO<RatingResponseDTO> response= new ResponseDTO<>(500, "Internal Server Error", null);
           return ResponseEntity.status(response.getStatus_code()).body(response);
       }
    }
}
