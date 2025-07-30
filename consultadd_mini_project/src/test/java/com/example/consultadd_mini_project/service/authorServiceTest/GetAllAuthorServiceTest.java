package com.example.consultadd_mini_project.service.authorServiceTest;

import com.example.consultadd_mini_project.DTO.AuthorResponseDTO;
import com.example.consultadd_mini_project.DTO.BookResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.model.Author;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.service.authorService.GetAllAuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllAuthorServiceTest {

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private GetAllAuthorService getAllAuthorService;

    private Author author;

    @BeforeEach
    void setup() {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Test Book");

        author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("John Doe");
        author.setEmail("john@example.com");
        author.setBio("Bio here...");
        author.setImageURL("image-url".getBytes());
        author.setBooks(Set.of(book));
    }

    @Test
    void testGetAllAuthor_Success() {
        when(authorRepo.findAll()).thenReturn(List.of(author));

        ResponseEntity<ResponseDTO<List<AuthorResponseDTO>>> response = getAllAuthorService.getAllAuthor();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Fetched all authors", response.getBody().getMessage());
        assertEquals(1, response.getBody().getData().size());
        assertEquals(author.getEmail(), response.getBody().getData().get(0).getEmail());

        verify(authorRepo).findAll();
    }

    @Test
    void testGetAllAuthor_InternalServerError() {
        when(authorRepo.findAll()).thenThrow(new RuntimeException("DB failure"));

        ResponseEntity<ResponseDTO<List<AuthorResponseDTO>>> response = getAllAuthorService.getAllAuthor();

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal Server Error", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(authorRepo).findAll();
    }
}
