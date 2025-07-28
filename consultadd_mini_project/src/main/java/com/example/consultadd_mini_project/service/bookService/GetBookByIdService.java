package com.example.consultadd_mini_project.service.bookService;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.consultadd_mini_project.DTO.BookResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.model.Book;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GetBookByIdService {
    @Autowired
    BookRepo repo;

    public ResponseEntity<ResponseDTO<BookResponseDTO>> getBookById(UUID id){
        try{
            Optional<Book> book= repo.findById(id);

            Optional<BookResponseDTO> bookResponseDTO= book.map(currentBook->{
                double avgRating = currentBook.getReviews().stream()
                        .mapToInt(review-> review.getValue())
                        .average()
                        .orElse(0.0);

                return BookResponseDTO.builder()
                        .id(currentBook.getId())
                        .title(currentBook.getTitle())
                        .summary(currentBook.getSummary())
                        .isbn(currentBook.getIsbn())
                        .coverImageUrl(currentBook.getCoverImageURL())
                        .publicationYear(currentBook.getPublicationYear())
                        .genres(currentBook.getGenres().stream()
                                .map(genre-> genre.getName())
                                .collect(Collectors.toList()))
                        .authors(currentBook.getAuthors().stream()
                                .map(author -> author.getEmail())
                                .collect(Collectors.toList()))
                        .ratings(currentBook.getReviews().stream()
                                .map(review-> review.getValue()).collect(Collectors.toList()))
                        .averageRating(avgRating)
                        .build();
            });
            ResponseDTO<BookResponseDTO> response;
            if(bookResponseDTO.isPresent()){
                response = new ResponseDTO<>(200, "Book is fetched", bookResponseDTO.get());
            }else{
                response = new ResponseDTO<>(400, "Book not found", null);
            }
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<BookResponseDTO> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
