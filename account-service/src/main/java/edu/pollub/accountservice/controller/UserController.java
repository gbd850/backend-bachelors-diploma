package edu.pollub.accountservice.controller;

import edu.pollub.accountservice.dto.UserRequest;
import edu.pollub.accountservice.model.User;
import edu.pollub.accountservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody UserRequest user) {
        return userService.saveUser(user);
    }

}
