package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@ToString(exclude = {"followers", "following", "likedReviews", "dislikedReviews", "readingGoals", "bookLists"})

@Entity
@Table(name="user_table")
public class User {
    public enum Roles{USER, ADMIN, MODERATOR};

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private UUID id;

    @Column(name="user_username")
    private String username;

    @Column(name="user_email")
    private String email;

    @Column(name="user_password")
    private String password;

    @Column(name="user_bio")
    private String bio;

    @Column(name="user_avatarURL")
    private String avatarURL;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private Roles role;

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers;

    @ManyToMany(mappedBy="followers")
    private Set<User> following;

    @ManyToMany(mappedBy="likes")
    private Set<Review> likedReviews;

    @ManyToMany(mappedBy="dislikes")
    private Set<Review> dislikedReviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingGoal> readingGoals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookList> bookLists;

}
