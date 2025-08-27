package com.example.Split.XpenseSplit.controller;

import com.example.Split.XpenseSplit.model.GroupDetails;
import com.example.Split.XpenseSplit.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupsController {
    private final GroupService groupService;
    @GetMapping("/user/{username}")
    public ResponseEntity<List<GroupDetails>> getGroupsForUser(@PathVariable String username) {
        List<GroupDetails> groups = groupService.getGroupsByUser(username);
        return ResponseEntity.ok(groups);
    }
    @PostMapping("/create")
    public ResponseEntity<GroupDetails> createGroup(@RequestBody GroupDetails groupDetails) {
        GroupDetails savedGroup = groupService.saveGroup(groupDetails);
        return ResponseEntity.ok(savedGroup);
    }
}
