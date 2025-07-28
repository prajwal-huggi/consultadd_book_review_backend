package com.example.consultadd_mini_project.DTO.commentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    private UUID replyId;
    private UUID reviewId;
    private UUID userId;
    private String username;
    private String email;
    private String replyText;
    private UUID parentCommentId;
    private LocalDateTime createdAt;

    private List<CommentResponseDTO> replies;
}
