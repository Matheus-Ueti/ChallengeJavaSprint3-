package com.example.challengejavasprint2.repository;

import com.example.challengejavasprint2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos básicos do JPA já estão disponíveis
}
