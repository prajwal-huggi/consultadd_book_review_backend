package com.example.consultadd_mini_project.DTO.reviewDTO;

import com.example.consultadd_mini_project.DTO.commentDTO.CommentResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private UUID reviewId;
    private String reviewText;
    private UUID userId;
    private UUID bookId;
    private String username;
    private String email;
    private Integer likes;
    private Integer dislikes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer rating;

    private List<CommentResponseDTO> replies;
}
