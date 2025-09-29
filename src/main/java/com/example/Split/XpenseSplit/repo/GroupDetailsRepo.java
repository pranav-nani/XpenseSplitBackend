package com.example.Split.XpenseSplit.repo;

import com.example.Split.XpenseSplit.model.Expense;
import com.example.Split.XpenseSplit.model.GroupDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupDetailsRepo {
    private final MongoOperations mongoOperations;
    public List<GroupDetails> findByMembersContaining(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("members").in(username));
        return mongoOperations.find(query, GroupDetails.class);
    }

    public GroupDetails save(GroupDetails groupDetails) {
        return mongoOperations.save(groupDetails);
    }

    public Expense saveExpenseToGroup(String groupId, Expense expense) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(groupId));
        GroupDetails grpDetails = mongoOperations.findOne(query, GroupDetails.class);
        if(grpDetails != null){
            grpDetails.getExpenses().add(expense);
            mongoOperations.save(grpDetails);
            return expense;
        } else {
            throw new RuntimeException("Group not found with id: " + groupId);
        }
    }

    public Optional<Object> findById(String groupId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(groupId));
        GroupDetails grpDetails = mongoOperations.findOne(query, GroupDetails.class);
        return Optional.ofNullable(grpDetails);
    }
    public GroupDetails findByIdV1(String groupId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(groupId));
        GroupDetails grpDetails = mongoOperations.findOne(query, GroupDetails.class);
        return grpDetails;
    }
}
