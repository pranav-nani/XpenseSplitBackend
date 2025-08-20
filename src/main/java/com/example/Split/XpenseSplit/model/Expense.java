package com.example.Split.XpenseSplit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document("expenses")
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    private String id;
    private String description;
    private double amount;
    private String category;
    private String paidBy;
    private Map<String, Double> splitWith;
}
