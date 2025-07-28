package com.example.consultadd_mini_project.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeAndDislikeRequestDTO {
    private UUID userId;
    private UUID reviewId;
}
