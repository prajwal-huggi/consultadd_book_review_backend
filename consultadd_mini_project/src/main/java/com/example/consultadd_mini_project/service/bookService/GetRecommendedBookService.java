package com.example.consultadd_mini_project.service.bookService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GetRecommendedBookService {
    @Autowired
    BookRepo repo;

    public ResponseEntity<ResponseDTO<List<Book>>> getRecommendedBook(){
        try{
            List<Book> allBooks= repo.findAll();
            Collections.shuffle(allBooks);
            List<Book> recommendedBooks= allBooks.stream().limit(5).toList();

            ResponseDTO<List<Book>> response= new ResponseDTO<>(200, "Recommended Books fetched", recommendedBooks);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<List<Book>> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
