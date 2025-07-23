package com.example.consultadd_mini_project.controller;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.service.bookService.CreateBookService;
import com.example.consultadd_mini_project.service.bookService.GetBestSellerService;
import com.example.consultadd_mini_project.service.bookService.GetNewlyAddedBooksService;
import com.example.consultadd_mini_project.service.bookService.GetRecommendedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/book/create")
//    @PreAuthorize(ADMIN)
    public ResponseEntity<ResponseDTO<Object>> addBook(
            @RequestParam("title") String title,
            @RequestParam("summary") String summary,
            @RequestParam("isbn") String isbn,
            @RequestParam("coverImage")MultipartFile coverImage
            ){
        return createBookService.saveBook(title, summary, isbn, coverImage);
    }

//    @PatchMapping("/book/update")
//    @PreAuthorize()

//    @DeleteMapping("/book/deleteBook")
//    @PreAuthorize()


    @GetMapping("/book/getNewlyAdded")
    public ResponseEntity<ResponseDTO<List<Book>>> getNewlyAdded(){
        return getNewlyAddedBooksService.getNewlyAddedBooks();
    }
    @GetMapping("/book/getBestSeller")
    public ResponseEntity<ResponseDTO<List<Book>>> getBestSeller(){
        return getBestSellerService.getBestSeller();
    }
    @GetMapping("/book/getRecommended")
    public ResponseEntity<ResponseDTO<List<Book>>> getRecommendedBooks(){
        return getRecommendedBookService.getRecommendedBook();
    }
}
