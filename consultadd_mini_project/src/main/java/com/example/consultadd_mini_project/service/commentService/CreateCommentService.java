package com.example.consultadd_mini_project.service.commentService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.DTO.commentDTO.CommentRequestDTO;
import com.example.consultadd_mini_project.DTO.commentDTO.CommentResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.Repository.CommentRepo;
import com.example.consultadd_mini_project.Repository.ReviewRepo;
import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Comment;
import com.example.consultadd_mini_project.model.Review;
import com.example.consultadd_mini_project.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CreateCommentService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private CommentRepo commentRepo;

    public ResponseEntity<ResponseDTO<CommentResponseDTO>> createComment(CommentRequestDTO commentRequestDTO){
        try{
            Optional<User> user= userRepo.findById(commentRequestDTO.getUserId());
            Optional<Review> review= commentRequestDTO.getReviewId()== null? Optional.empty(): reviewRepo.findById(commentRequestDTO.getReviewId());
            Optional<Book> book= bookRepo.findById(commentRequestDTO.getBookId());
            Optional<Comment> parentCommentId= commentRequestDTO.getParentCommentId()== null? Optional.empty() : commentRepo.findById(commentRequestDTO.getParentCommentId());

            if(user.isEmpty()){
                ResponseDTO<CommentResponseDTO> response= new ResponseDTO<>(404, "User not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }
            if(book.isEmpty()){
                ResponseDTO<CommentResponseDTO> response= new ResponseDTO<>(404, "book not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            Comment comment= new Comment();
            comment.setContent(commentRequestDTO.getContent());
            comment.setBook(book.get());
            comment.setUser(user.get());

            //Comment is on review
            if(review.isPresent()){
                comment.setReview(review.get());
            }

            //Comment is on comment
//            else if(parentCommentId.isPresent()){
//                comment.setParentComment(parentCommentId.get());
//            }
            else if(parentCommentId.isPresent()){
                Comment parent = parentCommentId.get();
                comment.setParentComment(parent);
                comment.setReview(parent.getReview());
            }

            else{
                ResponseDTO<CommentResponseDTO> response= new ResponseDTO<>(400, "Either comment or review should be present", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            Comment savedComment= commentRepo.save(comment);
            CommentResponseDTO commentResponseDTO= CommentResponseDTO.builder()
                    .replyId(savedComment.getId())
                    .reviewId(savedComment.getReview()!= null? savedComment.getReview().getId(): null)
                    .userId(savedComment.getUser().getId())
                    .username(savedComment.getUser().getUsername())
                    .email(savedComment.getUser().getEmail())
                    .replyText(savedComment.getContent())
                    .parentCommentId(savedComment.getParentComment()!= null? savedComment.getParentComment().getId():null)
                    .createdAt(savedComment.getCreatedAt())
                    .build();

            ResponseDTO<CommentResponseDTO> response= new ResponseDTO<>(200, "comment created successfully", commentResponseDTO);
            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO<CommentResponseDTO> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}