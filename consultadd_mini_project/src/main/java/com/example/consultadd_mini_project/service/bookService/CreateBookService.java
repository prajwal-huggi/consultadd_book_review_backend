package com.example.consultadd_mini_project.service.bookService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.AuthorRepo;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.Repository.GenreRepo;
import com.example.consultadd_mini_project.model.Author;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Genre;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreateBookService {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private GenreRepo genreRepo;

    private Boolean isUniqueBook(String isbn){
        Optional<Book> findBook= bookRepo.findByIsbn(isbn);
        System.out.println(findBook);

        return findBook.isEmpty();
    }

    public ResponseEntity<ResponseDTO<Object>> saveBook(String title, String summary, String isbn, MultipartFile coverImage, Set<UUID> authorIds, Integer publicationYear, Set<UUID> genreIds){
        try{
            //If the book's isbn is same then return error
            if(!isUniqueBook(isbn)){
                ResponseDTO<Object> response= new ResponseDTO<>(400, "Isbn number can't be same", null);
                return ResponseEntity.status((response.getStatus_code())).body(response);
            }

            byte[] imageByte= coverImage.getBytes();
            Set<Author> authors= authorIds.stream().map(id-> authorRepo.findById(id).orElse(null)).collect(Collectors.toSet());
            if(authors.contains(null)){
                ResponseDTO<Object> response = new ResponseDTO<>(404, "One or more authors not found", null);
                return ResponseEntity.status(response.getStatus_code()).body(response);
            }

            Set<Genre> genres= genreIds.stream().map(id-> genreRepo.findById(id).orElse(null)).collect(Collectors.toSet());
            if(genres.contains(null)){
                ResponseDTO<Object> response= new ResponseDTO<>(404, "One or more genre not found", null);

                return ResponseEntity.status((response.getStatus_code())).body(response);
            }

            Book book= new Book();
            book.setTitle(title);
            book.setSummary(summary);
            book.setCoverImageURL(imageByte);
            book.setIsbn(isbn);
            book.setAuthors(authors);
            book.setPublicationYear(publicationYear);
            book.setGenres(genres);

            Book saveBook= bookRepo.save(book);
            ResponseDTO<Object> response= new ResponseDTO<>(200, "Book saved successfully", book);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO<Object> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
