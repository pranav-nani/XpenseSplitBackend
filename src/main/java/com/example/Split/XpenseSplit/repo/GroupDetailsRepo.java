package com.example.Split.XpenseSplit.repo;

import com.example.Split.XpenseSplit.model.GroupDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class GroupDetailsRepo {
    private final MongoOperations mongoOperations;
    public List<GroupDetails> findByMembersContaining(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createdBy").is(username));
        return mongoOperations.find(query, GroupDetails.class);
    }

    public GroupDetails save(GroupDetails groupDetails) {
        return mongoOperations.save(groupDetails);
    }
}
