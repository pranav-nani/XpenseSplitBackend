package com.example.Split.XpenseSplit.controller;

import com.example.Split.XpenseSplit.model.Expense;
import com.example.Split.XpenseSplit.service.BalanceDTO;
import com.example.Split.XpenseSplit.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    @GetMapping("/balance/{username}")
    public BalanceDTO getUserBalance(@PathVariable String username) {
        return expenseService.getUserBalances(username);
    }
    @PostMapping("/addExpense")
    public String addExpense(@RequestBody Expense expense) {
        expenseService.saveExpense(expense);
        return "Success";
    }
    @PostMapping("/categorize")
    public String categorizeExpense(@RequestBody Map<String, String> request) {
        String description = request.get("description");
        return expenseService.getSuggestedCategory(description);
    }
    @GetMapping("/{username}")
    public List<Expense> getExpensesByUser(@PathVariable String username) {
        return expenseService.getExpensesByUsername(username);
    }
}

