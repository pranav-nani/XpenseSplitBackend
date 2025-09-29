package com.example.Split.XpenseSplit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
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
    private String upId;
}