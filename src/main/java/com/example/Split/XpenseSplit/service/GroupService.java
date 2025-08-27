package com.example.Split.XpenseSplit.service;

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
}
