package com.example.Split.XpenseSplit.repo;

import com.example.Split.XpenseSplit.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExpenseRepo {
    private final MongoOperations mongoOperations;
    public List<Expense> getExpenses(String user) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("splitWith." + user).exists(true),
                Criteria.where("paidBy").is(user)
        ));
        return mongoOperations.find(query, Expense.class);
    }


    public void save(Expense expense) {
        mongoOperations.save(expense);
    }
}
