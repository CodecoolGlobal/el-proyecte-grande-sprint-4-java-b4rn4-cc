package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Builder.Default
    private LocalDateTime transactionTime = LocalDateTime.now();
    private BigDecimal amount;
    private CurrencyType currency;
    @OneToOne
    private CheckingAccount sender;
    @OneToOne
    private Account recipient;
    @Builder.Default
    private String message = "";
    @Builder.Default
    private TransactionStatus status = TransactionStatus.PROCESSING;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void cloneBillTransaction(Transaction t) {
        this.id = null;
        this.transactionTime = null;
        this.sender = null;
        this.amount = t.getAmount();
        this.recipient = t.getRecipient();
        this.currency = t.getCurrency();
        this.status = TransactionStatus.PROCESSING;
        this.message = t.getMessage();
    }
}
