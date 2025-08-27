package com.example.Split.XpenseSplit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("groupDetails")
public class GroupDetails {
    @Id
    private String id;
    private String createdBy;
    private String groupName;
    private List<String> members;
    private List<Expense> expenses;
    private Instant createdDate = Instant.now();
}
