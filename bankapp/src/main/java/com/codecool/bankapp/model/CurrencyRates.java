package com.codecool.bankapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class CurrencyRates {
    @Id
    private Long id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private CurrencyType base = CurrencyType.EUR;
    @OneToMany
    @ToString.Exclude
    private List<Rate> rates;

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
        return rates.stream()
                .filter(r -> r.getSymbol().equals(currencyType))
                .toList().get(0).getValue();
    }
}
