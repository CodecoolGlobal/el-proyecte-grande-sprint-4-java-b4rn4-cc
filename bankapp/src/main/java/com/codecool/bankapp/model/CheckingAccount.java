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
@Entity
@NoArgsConstructor
public class CheckingAccount extends Account {
    {
        super.canWithdraw = true;
    }
    @Builder.Default
    private String type = CheckingAccount.class.getSimpleName();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CheckingAccount that = (CheckingAccount) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
