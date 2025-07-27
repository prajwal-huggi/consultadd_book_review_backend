package com.example.consultadd_mini_project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude= {"ratings","reviews","genres","authors"})
@Builder
@Entity
@Table(name="book_table")
@NoArgsConstructor
@AllArgsConstructor
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

    @Lob
    @Column(name="book_cover_image_url")
    private byte[] coverImageURL;

    @Column(name="book_addedAt")
    private LocalDateTime createdAt;

    @Column(name="book_updatedAt")
    private LocalDateTime updatedAt;

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

    @PrePersist
    protected void onCreate(){
        this.createdAt= LocalDateTime.now();
        this.updatedAt= LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt= LocalDateTime.now();
    }
}
