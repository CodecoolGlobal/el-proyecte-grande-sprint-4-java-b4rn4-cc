package com.codecool.bankapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findUserByUserIDEquals(UUID userID);
}
