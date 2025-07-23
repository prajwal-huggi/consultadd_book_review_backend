package com.example.consultadd_mini_project.Repository;

import com.example.consultadd_mini_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    public User findByEmail(String email);
}
