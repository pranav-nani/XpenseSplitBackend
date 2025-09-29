package com.example.Split.XpenseSplit.service;

import com.example.Split.XpenseSplit.dto.SettlementDTO;
import com.example.Split.XpenseSplit.model.Expense;
import com.example.Split.XpenseSplit.model.GroupDetails;
import com.example.Split.XpenseSplit.repo.GroupDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public GroupDetails settleUp(String groupId, SettlementDTO settlementDto) {

        GroupDetails group = groupDetailsRepo.findByIdV1(groupId);
        Expense settlementExpense = new Expense();
        settlementExpense.setId(UUID.randomUUID().toString());
        settlementExpense.setDescription(
                String.format("Settlement: %s paid %s", settlementDto.getPayer(), settlementDto.getPayee())
        );
        settlementExpense.setAmount(settlementDto.getAmount());
        settlementExpense.setPaidBy(settlementDto.getPayer());
        settlementExpense.setCategory("Settlement");

        settlementExpense.setSplitWith(Map.of(settlementDto.getPayee(), settlementDto.getAmount()));

        group.getExpenses().add(settlementExpense);

        return groupDetailsRepo.save(group);
    }
}
