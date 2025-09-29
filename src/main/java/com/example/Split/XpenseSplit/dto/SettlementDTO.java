package com.example.Split.XpenseSplit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementDTO {
    private String payer;
    private String payee;
    private double amount;
}
