package com.example.consultadd_mini_project.service.bookService;

import com.example.consultadd_mini_project.DTO.BookResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Genre;
import com.example.consultadd_mini_project.model.Rating;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GetBestSellerService {
    @Autowired
    BookRepo repo;

    public ResponseEntity<ResponseDTO<List<BookResponseDTO>>> getBestSeller(){
        try{
            List<Book> books= repo.findAll();
            books.sort((b1, b2)->{
                double avg1= b1.getRatings().stream().mapToInt(Rating::getValue).average().orElse(0.0);
                double avg2= b2.getRatings().stream().mapToInt(Rating::getValue).average().orElse(0.0);

                return Double.compare(avg2, avg1);
            });

            List<BookResponseDTO> bookDTOs = books.stream().map(book -> {
                double avgRating = book.getRatings().stream()
                        .mapToInt(Rating::getValue)
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
                        .ratings(book.getRatings().stream()
                                .map(Rating::getValue)
                                .collect(Collectors.toList()))
                        .averageRating(avgRating)
                        .build();
            }).collect(Collectors.toList());

            ResponseDTO<List<BookResponseDTO>> response= new ResponseDTO<>(200, "Fetched all best books ", bookDTOs);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch (Exception e){
            ResponseDTO<List<BookResponseDTO>> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
