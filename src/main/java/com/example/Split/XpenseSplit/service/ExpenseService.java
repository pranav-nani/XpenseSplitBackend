package com.example.Split.XpenseSplit.service;

import com.example.Split.XpenseSplit.model.Expense;
import com.example.Split.XpenseSplit.repo.ExpenseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    private final ExpenseRepo expenseRepo;
    private final RestTemplate restTemplate;

    public BalanceDTO getUserBalances(String username) {
        List<Expense> expenses = expenseRepo.getExpenses(username);
        double youOwe = 0.0;
        double youAreOwed = 0.0;

        for (Expense exp : expenses) {
            String paidBy = exp.getPaidBy();
            Map<String, Double> splits = exp.getSplitWith();

            if (paidBy.equals(username)) {
                // You paid → others owe you their shares
                for (Map.Entry<String, Double> entry : splits.entrySet()) {
                    String participant = entry.getKey();
                    Double amount = entry.getValue();
                    if (!participant.equals(username)) { // Ignore your own name if present
                        youAreOwed += amount;
                    }
                }
            } else {
                // Someone else paid → check your share
                Double yourShare = splits.get(username);
                if (yourShare != null) {
                    youOwe += yourShare;
                }
            }
        }

        double totalBalance = youAreOwed - youOwe;
        return new BalanceDTO(totalBalance, youOwe, youAreOwed);
    }

    public void saveExpense(Expense expense) {
        if (expense.getSplitWith() == null || expense.getSplitWith().isEmpty()) {
            throw new IllegalArgumentException("Expense must be split with at least one user.");
        }
        if (expense.getPaidBy() == null || expense.getPaidBy().isEmpty()) {
            throw new IllegalArgumentException("Expense must have a payer.");
        }
        expenseRepo.save(expense);
    }

    public String getSuggestedCategory(String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String prompt = "Based on the expense description, suggest a single, relevant category. " +
                "The possible categories are: Food, Transport, Shopping, Utilities, Entertainment, Health, Travel, Other. " +
                "Description: \"" + description + "\". " +
                "Respond with ONLY the category name and nothing else.";

        Map<String, Object> requestBody = new HashMap<>();

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        requestBody.put("messages", Collections.singletonList(message));
        requestBody.put("model", "llama3-8b-8192");
        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 10);
        requestBody.put("top_p", 1);
        requestBody.put("stream", false);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            Map response = restTemplate.postForObject(apiUrl, entity, Map.class);

            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    if (firstChoice.containsKey("message")) {
                        Map<String, String> messageContent = (Map<String, String>) firstChoice.get("message");
                        return messageContent.get("content").trim();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error calling AI API: " + e.getMessage());
            return "Other";
        }

        return "Other";
    }

    public List<Expense> getExpensesByUsername(String username) {
        List<Expense> expenses = expenseRepo.getExpenses(username);
        if (expenses == null || expenses.isEmpty()) {
            throw new IllegalArgumentException("No expenses found for user: " + username);
        }
        return expenses;
    }
}

