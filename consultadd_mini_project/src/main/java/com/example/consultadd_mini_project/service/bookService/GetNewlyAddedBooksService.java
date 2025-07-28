package com.example.consultadd_mini_project.service.bookService;

import java.util.Comparator;
import java.util.List;
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
public class GetNewlyAddedBooksService {
    @Autowired
    BookRepo repo;

    public ResponseEntity<ResponseDTO<List<BookResponseDTO>>> getNewlyAddedBooks(){
        try{
            List<Book> getAllBooks= repo.findAll();
            List<Book> getNewlyAddedBooks= getAllBooks.stream()
                    .sorted(Comparator
                            .comparing(Book::getCreatedAt)
                            .reversed())
                    .limit(12)
                    .collect(Collectors.toList());

            List<BookResponseDTO> bookDTOs= getNewlyAddedBooks.stream().map(book->{
                double avgRating= book.getReviews().stream()
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
                                .map(genre-> genre.getName())
                                .collect(Collectors.toList()))
                        .authors(book.getAuthors().stream()
                                .map(author-> author.getEmail())
                                .collect(Collectors.toList()))
                        .ratings(book.getReviews().stream()
                                .map(review-> review.getValue()).collect(Collectors.toList()))
                        .averageRating(avgRating)
                        .build();
            }).collect(Collectors.toList());

            ResponseDTO<List<BookResponseDTO>> response= new ResponseDTO<>(200, "Newly Added books fetched", bookDTOs);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<List<BookResponseDTO>> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
