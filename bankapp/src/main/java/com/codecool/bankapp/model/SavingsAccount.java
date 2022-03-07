package com.codecool.bankapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Objects;

@SuperBuilder
@Getter
@NoArgsConstructor
@Entity
public class SavingsAccount extends Account{
    {
        super.canWithdraw = false;
    }
    @Builder.Default
    private String type = SavingsAccount.class.getSimpleName();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SavingsAccount that = (SavingsAccount) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
