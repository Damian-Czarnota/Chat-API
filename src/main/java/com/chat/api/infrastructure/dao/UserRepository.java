package com.chat.api.infrastructure.dao;

import com.chat.api.infrastructure.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Boolean existsByLogin(String username);
    Boolean existsByEmail(String email);
    List<User> findAll();
}
