package com.codecool.bankapp.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class SavingsAccount extends Account{
    @Builder.Default
    private final String type = SavingsAccount.class.getSimpleName();
    @Builder.Default
    private final boolean canWithdraw = false;
}
