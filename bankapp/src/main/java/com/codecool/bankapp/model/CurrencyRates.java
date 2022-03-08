package com.codecool.bankapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class CurrencyRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private CurrencyType base = CurrencyType.EUR;
    @OneToMany
    @ToString.Exclude
    private List<Rate> ratesList = new ArrayList<>();
    @Transient
    private Map<CurrencyType, BigDecimal> rates;


    public void unpackRates(Map<CurrencyType, BigDecimal> rates) {
        for (CurrencyType currencyType : rates.keySet()) {
            Rate rate = new Rate();
            rate.setSymbol(currencyType);
            rate.setValue(rates.get(currencyType));
            this.ratesList.add(rate);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CurrencyRates that = (CurrencyRates) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public BigDecimal getRateBysymbol(CurrencyType currencyType) {
        return ratesList.stream()
                .filter(r -> r.getSymbol().equals(currencyType))
                .toList().get(0).getValue();
    }
}
