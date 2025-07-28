package com.example.consultadd_mini_project.service.reviewService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.DTO.commentDTO.CommentResponseDTO;
import com.example.consultadd_mini_project.DTO.reviewDTO.ReviewResponseDTO;
import com.example.consultadd_mini_project.Repository.ReviewRepo;
import com.example.consultadd_mini_project.model.Comment;
import com.example.consultadd_mini_project.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class GetReviewService {
    @Autowired
    private ReviewRepo reviewRepo;

    public ResponseEntity<ResponseDTO<List<ReviewResponseDTO>>> getReview(UUID bookId){
        try{
            Optional<List<Review>> reviews= reviewRepo.findAllByBookId(bookId);

            if(reviews.isEmpty()){
                ResponseDTO<List<ReviewResponseDTO>> response= new ResponseDTO<>(400, "No reviews present for the book", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            List<ReviewResponseDTO> reviewDTOs= reviews.get().stream().map(review->{
                        List<Comment> allComments= review.getComments();

                List<CommentResponseDTO> topLevelReplies = allComments.stream()
//                        .filter(c -> c.getParentComment() == null)
                                .filter(c -> c.getParentComment() == null && c.getReview() != null && c.getReview().getId().equals(review.getId()))
                                .map(comment -> CommentResponseDTO.builder()
                                .replyId(comment.getId())
                                .reviewId(review.getId())
                                .userId(comment.getUser().getId())
                                .username(comment.getUser().getUsername())
                                .email(comment.getUser().getEmail())
                                .replyText(comment.getContent())
                                .createdAt(comment.getCreatedAt())
                                .parentCommentId(null)
                                .replies(buildNestedReplies(comment, allComments, review.getId()))
                                .build())
                        .collect(Collectors.toList());
                return ReviewResponseDTO.builder()
                        .reviewId(review.getId())
                        .reviewText(review.getContent())
                        .userId(review.getUser().getId())
                        .bookId(review.getBook().getId())
                        .username(review.getUser().getUsername())
                        .email(review.getUser().getEmail())
                        .likes(review.getLikes() != null ? review.getLikes().size() : 0)
                        .dislikes(review.getDislikes() != null ? review.getDislikes().size() : 0)
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .rating(review.getValue())
                        .replies(topLevelReplies)
                        .build();
                    }
            ).collect(Collectors.toList());

            ResponseDTO<List<ReviewResponseDTO>> response= new ResponseDTO<>(200, "Reviews fetched successfully", reviewDTOs);
            return ResponseEntity.status(response.getStatus_code()).body(response);

        }catch(Exception e){
            ResponseDTO<List<ReviewResponseDTO>> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }

    }
        private List<CommentResponseDTO> buildNestedReplies(Comment parent, List<Comment> allComments, UUID reviewId) {
            return allComments.stream()
                    .filter(c -> c.getParentComment() != null && c.getParentComment().getId().equals(parent.getId()))
                    .map(child -> CommentResponseDTO.builder()
                            .replyId(child.getId())
                            .reviewId(reviewId)
                            .userId(child.getUser().getId())
                            .username(child.getUser().getUsername())
                            .email(child.getUser().getEmail())
                            .replyText(child.getContent())
                            .createdAt(child.getCreatedAt())
                            .parentCommentId(parent.getId())
                            .replies(buildNestedReplies(child, allComments, reviewId))
                            .build())
                    .collect(Collectors.toList());
        }
}
