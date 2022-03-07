package com.codecool.bankapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "bank_user")
public class User {
    @Id
    private Long id;
    @Builder.Default
    private UUID userID = UUID.randomUUID();
    private String name;
    private String address;
    private String password;
    @OneToMany
    @ToString.Exclude
    private List<Account> accountList;

    public void addAccountToList(Account account) {
        accountList.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
