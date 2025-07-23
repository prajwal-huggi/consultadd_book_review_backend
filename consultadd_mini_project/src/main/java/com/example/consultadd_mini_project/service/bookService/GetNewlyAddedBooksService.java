package com.example.consultadd_mini_project.service.bookService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.model.Book;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetNewlyAddedBooksService {
    @Autowired
    BookRepo repo;

    public ResponseEntity<ResponseDTO<List<Book>>> getNewlyAddedBooks(){
        try{
            List<Book> getAllBooks= repo.findAll();
            List<Book> getNewlyAddedBooks= getAllBooks.stream().sorted(Comparator.comparing(Book::getCreatedAt).reversed()).limit(12).collect(Collectors.toList());

            ResponseDTO<List<Book>> response= new ResponseDTO<>(200, "Newly Added books fetched", getNewlyAddedBooks);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch(Exception e){
            ResponseDTO<List<Book>> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
