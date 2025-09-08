package com.example.Split.XpenseSplit.service;

import com.example.Split.XpenseSplit.model.Expense;
import com.example.Split.XpenseSplit.model.GroupDetails;
import com.example.Split.XpenseSplit.repo.GroupDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupDetailsRepo groupDetailsRepo;
    public List<GroupDetails> getGroupsByUser(String username) {
        return groupDetailsRepo.findByMembersContaining(username);
    }

    public GroupDetails saveGroup(GroupDetails groupDetails) {
        return groupDetailsRepo.save(groupDetails);
    }

    public Expense addExpenseToGroup(String groupId, Expense expense) {
        return groupDetailsRepo.saveExpenseToGroup(groupId, expense);
    }

    public GroupDetails getGroupsByGroupId(String groupId) {
        return (GroupDetails) groupDetailsRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
    }
}
