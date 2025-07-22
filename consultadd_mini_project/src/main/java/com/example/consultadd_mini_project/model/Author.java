package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue
    @Column(name="author_id")
    private UUID id;

    @Column(name="author_name")
    private String name;

    @Column(name="author_bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name="author_image_url")
    private String imageURL;

    @ManyToMany(mappedBy = "authors")
    Set<Book> books;
}
