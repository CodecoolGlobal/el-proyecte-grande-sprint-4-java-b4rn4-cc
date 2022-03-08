package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Builder.Default
    protected UUID accountNumber = UUID.randomUUID();
    protected UUID userID;
    @Builder.Default
    protected BigDecimal balance = new BigDecimal("0");
    @Builder.Default
    protected CurrencyType currency = CurrencyType.EUR;
    @Transient
    @JsonIgnore
    protected boolean canWithdraw;
    @JsonBackReference
    @ManyToMany
    @ToString.Exclude
    protected List<Transaction> history = new ArrayList<>();

    public void setHistory(List<Transaction> history) {
        this.history = history;
    }

    public void depositMoney(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public boolean withdrawMoney(BigDecimal amount) {
        if(canWithdraw) {
            this.balance = balance.subtract(amount);
        }
        return canWithdraw;
    }

    public void addToHistory(Transaction transaction) {
        history.add(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return accountNumber != null && Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

