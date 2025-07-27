package com.example.consultadd_mini_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude={"books"})

@Entity
@Table(name="genre_table")
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonBackReference
    Set<Book> books;
}
