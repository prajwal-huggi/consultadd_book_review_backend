package com.example.consultadd_mini_project.DTO.ratingDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequestDTO {
    private int value;
    private UUID userId;
    private UUID bookId;
}
