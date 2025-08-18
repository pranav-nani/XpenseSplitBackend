package com.example.Split.XpenseSplit.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String passwordHash;
}