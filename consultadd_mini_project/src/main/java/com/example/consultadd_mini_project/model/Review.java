package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="review_table")
public class Review {
    @Id
    @GeneratedValue
    @Column(name="review_id")
    private UUID id;

    @Column(name="review_content", columnDefinition="TEXT")
    private String content;

    @Column(name="review_rating")
    private Integer value;

    @Column(name="review_createdAt")
    private LocalDateTime createdAt;

    @Column(name="review_updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToMany
    @JoinTable(
            name="review_likes",
            joinColumns= @JoinColumn(name="review_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> likes;

    @ManyToMany
    @JoinTable(
            name="review_dislikes",
            joinColumns= @JoinColumn(name="review_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> dislikes;

    //Runs only once when the entity is created
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //Runs whenever the entity is updated
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
