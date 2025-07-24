package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Data
@ToString(exclude={"books"})

@Entity
@Table(name="genre_table")
public class Genre {
    @Id
    @GeneratedValue
    @Column(name="genre_id")
    private UUID id;

    @Column(name="genre_name")
    private String name;

    @Column(name="genre_description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "genres")
    Set<Book> books;
}
