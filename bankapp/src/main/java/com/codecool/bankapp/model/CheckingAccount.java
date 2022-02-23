package com.codecool.bankapp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
public class CheckingAccount extends Account {
    {
        super.canWithdraw = true;
    }
    @Builder.Default
    private final String type = CheckingAccount.class.getSimpleName();;
}
