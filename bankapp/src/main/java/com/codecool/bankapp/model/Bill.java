package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User payer;
    @ManyToOne
    private User recipient;
    @OneToOne
    private Transaction transaction;
    @Builder.Default
    private boolean paid = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return transaction.equals(bill.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction);
    }
}
