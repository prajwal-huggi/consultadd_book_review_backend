package com.example.consultadd_mini_project.Repository;

import com.example.consultadd_mini_project.model.ReadingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReadingGoalRepo extends JpaRepository<ReadingGoal, UUID> {
}
