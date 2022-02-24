package com.codecool.bankapp.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class CurrencyRates {
    private Date date;
    private CurrencyType base;
    private Map<CurrencyType, BigDecimal> rates = new HashMap<>();
}
