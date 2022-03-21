package com.codecool.bankapp.repository;

import com.codecool.bankapp.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {
}
