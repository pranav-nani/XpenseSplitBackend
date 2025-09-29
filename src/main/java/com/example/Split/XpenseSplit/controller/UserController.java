package com.example.Split.XpenseSplit.controller;


import com.example.Split.XpenseSplit.dto.UserDTO;
import com.example.Split.XpenseSplit.model.LoginRequest;
import com.example.Split.XpenseSplit.model.User;
import com.example.Split.XpenseSplit.repo.UserRepository;
import com.example.Split.XpenseSplit.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<String> getAllUsernames() {
        return userRepository.findAll()
                .stream()
                .map(User::getUsername)
                .toList();
    }

    @GetMapping("/upi/all")
    public List<UserDTO> getAllUsernamesUpis() {
        return userRepository.findAll()
                .stream()
                // Map each User entity to a UserDto object
                .map(user -> new UserDTO(user.getUsername(), user.getUpId()))
                .toList();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id){
        return userService.getUser(id);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody() User user, @PathVariable("id") Long id){
        return userService.updateUser(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody() User user){
        User newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Integer id){
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            User user = userService.findByUsernameAndPassword(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            session.setAttribute("user", user.getUsername());
            return ResponseEntity.ok(user);

        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            // wrong username OR password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (Exception e) {
            // any other server-side error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occurred");
        }
    }


}
