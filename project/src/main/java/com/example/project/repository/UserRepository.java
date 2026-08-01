package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import com.example.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username);

    @Query("SELECT COUNT(u) FROM User u")
    int countUsers();

}
