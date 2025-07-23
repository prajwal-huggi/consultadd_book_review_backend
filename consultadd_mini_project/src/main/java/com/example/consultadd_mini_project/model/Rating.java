package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString(exclude={"user","book"})

@Entity
@Table(name="rating_table")
public class Rating {
    @Id
    @GeneratedValue
    @Column(name="rating_id")
    private UUID id;

    @Column(name="rating_value")
    private Integer value;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

}
