package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="booklist_table")
public class BookList {
    @Id
    @GeneratedValue
    @Column(name="booklist_id")
    private UUID id;

    @Column(name="booklist_name")
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name="book_list",
            joinColumns= @JoinColumn(name= "booklist_id"),
            inverseJoinColumns = @JoinColumn(name="book_id")
    )
    private Set<Book> book;
}
