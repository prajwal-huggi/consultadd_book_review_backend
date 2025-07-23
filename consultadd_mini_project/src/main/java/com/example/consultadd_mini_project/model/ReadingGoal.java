package com.example.consultadd_mini_project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString(exclude={"user"})

@Entity
@Table(name="readingGoal_table")
public class ReadingGoal {
    @Id
    @GeneratedValue
    @Column(name="readingGoal_id")
    private UUID id;

    @Column(name="readingGoal_year")
    private Integer year;

    @Column(name="readingGoal_target_count")
    private Integer targetCount;

    @Column(name="readingGoal_current_count")
    private Integer currentCount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
