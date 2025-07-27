package com.example.consultadd_mini_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude={"books", "imageURL"})

@Entity
@Table(name="author_table")
public class Author {
    @Id
    @GeneratedValue
    @Column(name="author_id")
    private UUID id;

    @Column(name="author_name")
    private String name;

    @Column(name="author_bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name="author_email")
    private String email;

    @Lob
    @Column(name="author_image_url")
    private byte[] imageURL;

    @ManyToMany(mappedBy = "authors")
    Set<Book> books;
}
