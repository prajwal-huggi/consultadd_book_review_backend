package com.example.consultadd_mini_project.DTO.commentDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDTO {
    private UUID userId;
    private UUID reviewId;
    private UUID bookId;
    private String content;
    private UUID parentCommentId;
}
