package com.example.consultadd_mini_project.service.authorServiceTest;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.model.Author;
import com.example.consultadd_mini_project.service.authorService.CreateAuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAuthorServiceTest {

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private CreateAuthorService createAuthorService;

    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "dummy image content".getBytes()
        );
    }

    @Test
    void testCreateAuthor_Success() throws Exception {
        // Arrange
        String name = "John Doe";
        String bio = "Author bio";
        String email = "john@example.com";

        when(authorRepo.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ResponseDTO<Object>> response = createAuthorService.createAuthor(name, bio, email, mockFile);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Author saved in the database", response.getBody().getMessage());
        verify(authorRepo).save(any(Author.class));
    }

    @Test
    void testCreateAuthor_InvalidEmail() throws Exception {
        // Arrange
        String email = "invalid-email";

        // Act
        ResponseEntity<ResponseDTO<Object>> response = createAuthorService.createAuthor("Name", "Bio", email, mockFile);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Email id is not valid", response.getBody().getMessage());
        verify(authorRepo, never()).save(any());
    }

    @Test
    void testCreateAuthor_EmailAlreadyExists() throws Exception {
        // Arrange
        String email = "existing@example.com";
        when(authorRepo.findByEmail(email)).thenReturn(Optional.of(new Author()));

        // Act
        ResponseEntity<ResponseDTO<Object>> response = createAuthorService.createAuthor("Name", "Bio", email, mockFile);

        // Assert
        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Email id already exists", response.getBody().getMessage());
        verify(authorRepo, never()).save(any());
    }

    @Test
    void testCreateAuthor_InternalServerError() throws Exception {
        // Arrange
        String email = "valid@example.com";
        MultipartFile badFile = mock(MultipartFile.class);
        when(badFile.getBytes()).thenThrow(new RuntimeException("Failed to read file"));
        when(authorRepo.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ResponseDTO<Object>> response = createAuthorService.createAuthor("Name", "Bio", email, badFile);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal Server Error", response.getBody().getMessage());
        verify(authorRepo, never()).save(any());
    }
}