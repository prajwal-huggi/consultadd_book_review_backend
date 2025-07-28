package com.example.consultadd_mini_project.DTO;

import com.example.consultadd_mini_project.model.Book;
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
public class AuthorResponseDTO {
    private UUID id;
    private String name;
    private String bio;
    private String email;
    private byte[] imageURL;
    private List<BookResponseDTO> books;
}
