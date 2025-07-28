package com.example.consultadd_mini_project.Repository;

import com.example.consultadd_mini_project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepo extends JpaRepository<Review, UUID> {
    Optional<List<Review>> findAllByBookId(UUID bookId);
}
