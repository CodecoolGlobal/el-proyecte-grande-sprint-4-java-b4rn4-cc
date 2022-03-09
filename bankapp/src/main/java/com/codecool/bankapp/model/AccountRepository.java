package com.codecool.bankapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAccountNumberEquals(UUID accountNumber);

    @Query("select a from CheckingAccount a where a.accountNumber = ?1")
    Optional<CheckingAccount> findCheckingAccountByAccountNumberEquals(UUID accountNumber);
    List<Account> getAccountsByUserIDEquals(UUID userID);
}
