package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@ToString(exclude={"user","parentComment","review","book"})

@Entity
@Table(name="comment_table")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private UUID id;

    @Column(name="comment_content", columnDefinition="TEXT")
    private String content;

    @Column(name="comment_createdAt")
    private LocalDateTime createdAt;

    @Column(name="comment_updatedAt")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="parent_comment")
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

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
