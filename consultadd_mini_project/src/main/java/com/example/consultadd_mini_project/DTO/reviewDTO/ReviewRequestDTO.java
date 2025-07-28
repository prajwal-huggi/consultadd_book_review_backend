package com.example.consultadd_mini_project.DTO.reviewDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    private String content;
    private Integer value;
    private UUID userId;
    private UUID bookId;
}
