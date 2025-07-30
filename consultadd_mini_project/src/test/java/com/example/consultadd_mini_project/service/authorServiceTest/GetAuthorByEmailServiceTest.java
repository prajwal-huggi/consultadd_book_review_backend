package com.example.consultadd_mini_project.service.authorServiceTest;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.model.Author;
import com.example.consultadd_mini_project.service.authorService.GetAuthorByEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAuthorByEmailServiceTest {

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private GetAuthorByEmailService getAuthorByEmailService;

    private UUID authorId;
    private String email;
    private Author mockAuthor;

    @BeforeEach
    void setUp(){
        authorId= UUID.randomUUID();
        email= "author@gmail.com";

        mockAuthor= new Author();
        mockAuthor.setId(authorId);
        mockAuthor.setEmail(email);
        mockAuthor.setName("Test Author");
    }

    @Test
    void testGetAuthorIdByEmail_Success(){
        when(authorRepo.findByEmail(email)).thenReturn(Optional.of(mockAuthor));

        ResponseEntity<ResponseDTO<UUID>> response= getAuthorByEmailService.getAuthorIdByEmail(email);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(authorId, response.getBody().getData());
        assertEquals("Id of the author is fetched", response.getBody().getMessage());

        verify(authorRepo, times(1)).findByEmail(email);
    }
}
