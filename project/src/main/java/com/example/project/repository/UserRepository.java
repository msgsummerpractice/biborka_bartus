package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    default List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        findAll().forEach(users::add);
        return users;
    }

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void deleteById(Long id);

    <S extends User> S save(S entity);

    @Query("SELECT u FROM User u ORDER BY u.username")
    List<User> findTop10ByOrderByUsernameAsc();

    @Query("SELECT COUNT(u) FROM User u")
    int countUsers();

}
