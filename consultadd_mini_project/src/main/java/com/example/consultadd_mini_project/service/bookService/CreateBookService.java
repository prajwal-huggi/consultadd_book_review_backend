package com.example.consultadd_mini_project.service.bookService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CreateBookService {
    @Autowired
    private BookRepo repo;

    private Boolean isUniqueBook(String isbn){
        Book findBook= repo.findByIsbn(isbn);

        return findBook== null;
    }

    public ResponseEntity<ResponseDTO<Object>> saveBook(String title, String summary, String isbn, MultipartFile coverImage){
        try{
            //If the book's isbn is same then return error
            if(!isUniqueBook(isbn)){
                ResponseDTO<Object> response= new ResponseDTO<>(400, "Isbn number can't be same", null);
                return ResponseEntity.status((response.getStatus_code())).body(response);
            }

            byte[] imageByte= coverImage.getBytes();

            Book book= new Book();
            book.setTitle(title);
            book.setSummary(summary);
            book.setCoverImageURL(imageByte);
            book.setIsbn(isbn);

            Book saveBook= repo.save(book);
            ResponseDTO<Object> response= new ResponseDTO<>(200, "Book saved successfully", saveBook);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch (Exception e){
            ResponseDTO<Object> response= new ResponseDTO<>(500, "Internal Server Error", null);
            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
