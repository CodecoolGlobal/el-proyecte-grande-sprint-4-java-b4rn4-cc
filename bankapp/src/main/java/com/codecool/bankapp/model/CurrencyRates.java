package com.codecool.bankapp.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class CurrencyRates {
    private Date date;
    private CurrencyType base = CurrencyType.EUR;
    private Map<CurrencyType, BigDecimal> rates = new HashMap<>() {{
        put(CurrencyType.GBP, new BigDecimal("0.834781"));
        put(CurrencyType.JPY, new BigDecimal("128.328779"));
        put(CurrencyType.USD, new BigDecimal("1.116757"));
        put(CurrencyType.HUF, new BigDecimal("368.261915"));
    }};
}
