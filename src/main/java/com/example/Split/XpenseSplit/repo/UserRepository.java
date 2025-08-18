package com.example.Split.XpenseSplit.repo;


import com.example.Split.XpenseSplit.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository  {
    private final MongoOperations mongoOperations;
    public User findByUsername(String username) {
        return mongoOperations.findOne(
                Query.query(Criteria.where("username").is(username)),
                User.class
        );
    }

    public List<User> findAll() {
        return mongoOperations.findAll(User.class);
    }

    public Optional<Object> findById(Integer id) {
        return Optional.ofNullable(mongoOperations.findOne(
                Query.query(Criteria.where("id").is(id)),
                User.class
        ));
    }

    public User save(User user) {
        mongoOperations.save(user);
        return user;
    }

    public void deleteById(Integer id) {
        mongoOperations.remove(
                Query.query(Criteria.where("id").is(id)),
                User.class
        );
    }
}
