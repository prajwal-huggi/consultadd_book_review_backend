package com.example.consultadd_mini_project.DTO.RatingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponseDTO {

    private UUID id;
    private Integer value;
    private UUID userId;
    private UUID bookId;
}
