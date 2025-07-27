package com.example.consultadd_mini_project.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {
    private UUID id;
    private String title;
    private String summary;
    private String isbn;
    private byte[] coverImageUrl;
    private Integer publicationYear;
    private List<String> genres;
    private List<String> authors;
    private List<Integer> ratings;
    private Double averageRating;
}
