package com.example.consultadd_mini_project.service.bookService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.consultadd_mini_project.DTO.BookResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Genre;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GetRecommendedBookService {
    @Autowired
    BookRepo repo;

    public ResponseEntity<ResponseDTO<List<BookResponseDTO>>> getRecommendedBook(){
        try{
            List<Book> allBooks= repo.findAll();
            Collections.shuffle(allBooks);
            List<Book> recommendedBooks= allBooks.stream().limit(5).toList();

            List<BookResponseDTO> bookDTOs = recommendedBooks.stream().map(book -> {
                double avgRating = book.getReviews().stream()
                        .mapToInt(review-> review.getValue())
                        .average()
                        .orElse(0.0);

                return BookResponseDTO.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .summary(book.getSummary())
                        .isbn(book.getIsbn())
                        .coverImageUrl(book.getCoverImageURL())
                        .publicationYear(book.getPublicationYear())
                        .genres(book.getGenres().stream()
                                .map(Genre::getName)
                                .collect(Collectors.toList()))
                        .authors(book.getAuthors().stream()
                                .map(author -> author.getEmail())
                                .collect(Collectors.toList()))
                        .ratings(book.getReviews().stream()
                                .map(review-> review.getValue()).collect(Collectors.toList()))
                        .averageRating(avgRating)
                        .build();
            }).collect(Collectors.toList());

            ResponseDTO<List<BookResponseDTO>> response= new ResponseDTO<>(200, "Recommended Books fetched", bookDTOs);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<List<BookResponseDTO>> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}