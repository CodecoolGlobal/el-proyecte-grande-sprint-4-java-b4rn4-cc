package com.codecool.bankapp.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
public class SavingsAccount extends Account{
    {
        super.canWithdraw = false;
    }
    @Builder.Default
    private final String type = SavingsAccount.class.getSimpleName();
}
