package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@ToString(exclude= {"ratings","reviews","genres","authors"})

@Entity
@Table(name="book_table")
public class Book {
    @Id
    @GeneratedValue
    @Column(name="book_id")
    private UUID id;

    @Column(name="book_title")
    private String title;

    @Column(name="book_summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name="book_isbn")
    private String isbn;

    @Column(name="book_publication_year")
    private Integer publicationYear;

    @Column(name="book_cover_image_url")
    private String coverImageURL;

    @ManyToMany
    @JoinTable(
            name="book_author",
            joinColumns= @JoinColumn(name="book_id"),
            inverseJoinColumns= @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

}
