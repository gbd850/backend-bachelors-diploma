package edu.pollub.accountservice.controller;

import edu.pollub.accountservice.dto.LoginRequest;
import edu.pollub.accountservice.dto.TokenRequest;
import edu.pollub.accountservice.dto.UserRequest;
import edu.pollub.accountservice.model.User;
import edu.pollub.accountservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserRequest user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginDto){
        return new ResponseEntity<>(userService.authenticateUser(loginDto), HttpStatus.OK);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestBody TokenRequest tokenRequest) {
        return new ResponseEntity<>(userService.validateToken(tokenRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

}
