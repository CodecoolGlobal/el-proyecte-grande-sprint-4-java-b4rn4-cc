package com.codecool.bankapp.repository;

import com.codecool.bankapp.model.CurrencyRates;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrencyRatesRepository extends JpaRepository<CurrencyRates, Long> {
    CurrencyRates findFirstByOrderByIdDesc();
}
