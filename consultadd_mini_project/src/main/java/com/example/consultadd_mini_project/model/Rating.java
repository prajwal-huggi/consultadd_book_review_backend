package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
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

}
