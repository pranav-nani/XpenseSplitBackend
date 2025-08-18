package com.example.Split.XpenseSplit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "login_requests")
public class LoginRequest {

    @Id
    private String id;
    private String username;
    private String password;

}