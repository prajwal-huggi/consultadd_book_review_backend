package com.example.consultadd_mini_project.Repository;

import com.example.consultadd_mini_project.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepo extends JpaRepository<Book, UUID> {
    public Optional<Book> findByIsbn(String isbn);

    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN FETCH b.genres " +
            "LEFT JOIN FETCH b.authors " +
            "LEFT JOIN FETCH b.ratings")
    List<Book> findAllWithRelations();

}
