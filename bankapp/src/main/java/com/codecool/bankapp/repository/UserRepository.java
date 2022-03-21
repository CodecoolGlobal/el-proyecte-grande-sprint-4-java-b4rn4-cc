package com.codecool.bankapp.repository;

import com.codecool.bankapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findUserByUserIDEquals(UUID userID);
}
