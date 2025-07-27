package com.example.consultadd_mini_project.controller;

import com.example.consultadd_mini_project.DTO.BookResponseDTO;
import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.model.Author;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Genre;
import com.example.consultadd_mini_project.service.bookService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class BookController {
    @Autowired
    CreateBookService createBookService;

    @Autowired
    GetBestSellerService getBestSellerService;

    @Autowired
    GetRecommendedBookService getRecommendedBookService;

    @Autowired
    GetNewlyAddedBooksService getNewlyAddedBooksService;

    @Autowired
    GetAllBookService getAllBookService;

    @Autowired
    GetBookByIdService getBookByIdService;

    @PostMapping("/book/create")
//    @PreAuthorize(ADMIN)
    public ResponseEntity<ResponseDTO<Object>> addBook(
            @RequestParam("title") String title,
            @RequestParam("summary") String summary,
            @RequestParam("isbn") String isbn,
            @RequestParam("coverImage")MultipartFile coverImage,
            @RequestParam("authors")Set<UUID> authorIds,
            @RequestParam("publicationYear")Integer publicationYear,
            @RequestParam("genres")Set<UUID> genreIds
            ){
        return createBookService.saveBook(title, summary, isbn, coverImage, authorIds,publicationYear, genreIds);
    }

//    @PatchMapping("/book/update")
//    @PreAuthorize()

//    @DeleteMapping("/book/deleteBook")
//    @PreAuthorize()

    @GetMapping("/book/getAll")
    public ResponseEntity<ResponseDTO<List<BookResponseDTO>>> getAllBooks(){
        return getAllBookService.getAllBook();
    }

    @GetMapping("/book/getNewlyAdded")
    public ResponseEntity<ResponseDTO<List<BookResponseDTO>>> getNewlyAdded(){
        return getNewlyAddedBooksService.getNewlyAddedBooks();
    }

    @GetMapping("/book/getBookById")
    public ResponseEntity<ResponseDTO<BookResponseDTO>> getBookById(@RequestParam("id") UUID id){
        return getBookByIdService.getBookById(id);
    }

    @GetMapping("/book/getBestSeller")
    public ResponseEntity<ResponseDTO<List<BookResponseDTO>>> getBestSeller(){
        return getBestSellerService.getBestSeller();
    }

    @GetMapping("/book/getRecommended")
    public ResponseEntity<ResponseDTO<List<BookResponseDTO>>> getRecommendedBooks(){
        return getRecommendedBookService.getRecommendedBook();
    }
}
