package com.example.consultadd_mini_project.Repository;

import com.example.consultadd_mini_project.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepo extends JpaRepository<Rating, UUID> {
}
