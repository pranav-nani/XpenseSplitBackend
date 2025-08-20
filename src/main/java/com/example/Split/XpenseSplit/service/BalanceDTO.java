package com.example.Split.XpenseSplit.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceDTO {
    private double totalBalance;
    private double youOwe;
    private double youAreOwed;
}

