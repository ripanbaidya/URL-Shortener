package org.astrobrains.urlshortener.repository;

import org.astrobrains.urlshortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String username);

    // Check if email exists
    boolean existsByEmail(String email);
}
