package com.example.consultadd_mini_project.Repository;

import com.example.consultadd_mini_project.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepo extends JpaRepository<Genre, UUID> {
    public Optional<Genre> findByName(String name);
}
