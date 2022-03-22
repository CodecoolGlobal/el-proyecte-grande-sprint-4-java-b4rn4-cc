package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "bank_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Builder.Default
    @Column(unique = true)
    private UUID userID = UUID.randomUUID();
    private String name;
    private String address;
    private String password;
    @ManyToMany
    @ToString.Exclude
    private List<Role> roles;
    @OneToMany
    @Builder.Default
    @ToString.Exclude
    private List<Account> accountList = new ArrayList<>();

    public void addAccountToList(Account account) {
        accountList.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userID != null && Objects.equals(userID, user.userID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean areNullFields() {
        return Stream.of(name, address, password, userID).anyMatch(Objects::isNull);
    }
}
