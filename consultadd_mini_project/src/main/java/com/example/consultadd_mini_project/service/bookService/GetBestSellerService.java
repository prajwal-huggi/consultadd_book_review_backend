package com.example.consultadd_mini_project.service.bookService;

import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.Repository.BookRepo;
import com.example.consultadd_mini_project.model.Book;
import com.example.consultadd_mini_project.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBestSellerService {
    @Autowired
    BookRepo repo;

    public ResponseEntity<ResponseDTO<List<Book>>> getBestSeller(){
        try{
            List<Book> books= repo.findAll();
            books.sort((b1, b2)->{
                double avg1= b1.getRatings().stream().mapToInt(Rating::getValue).average().orElse(0.0);
                double avg2= b2.getRatings().stream().mapToInt(Rating::getValue).average().orElse(0.0);

                return Double.compare(avg2, avg1);

            });
            ResponseDTO<List<Book>> response= new ResponseDTO<>(200, "Fetched all books ", books);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }catch (Exception e){
            ResponseDTO<List<Book>> response= new ResponseDTO<>(500, "Internal Server Error", null);

            return ResponseEntity.status(response.getStatus_code()).body(response);
        }
    }
}
