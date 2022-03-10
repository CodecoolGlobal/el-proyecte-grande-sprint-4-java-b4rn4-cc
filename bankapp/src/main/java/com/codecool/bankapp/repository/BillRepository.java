package com.codecool.bankapp.repository;

import com.codecool.bankapp.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<List<Bill>> findBillsByPayerUserIDEquals(UUID userid);
}
