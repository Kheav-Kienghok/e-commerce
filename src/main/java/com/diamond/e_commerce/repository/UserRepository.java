package com.diamond.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diamond.e_commerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find a user by email.
   *
   * @param email the email to search
   * @return an Optional containing the User if found
   */
  Optional<User> findByEmail(String email);

  /**
   * Check if a user exists by email.
   *
   * @param email the email to check
   * @return true if a user with this email exists
   */
  boolean existsByEmail(String email);

}
