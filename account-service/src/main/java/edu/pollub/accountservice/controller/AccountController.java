package edu.pollub.accountservice.controller;

import edu.pollub.accountservice.dto.AccountResponse;
import edu.pollub.accountservice.repository.UserRepository;
import edu.pollub.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccountsBasedOnRole(
            @RequestParam(value = "role", defaultValue = "PARENT") String role
            ) {
        return new ResponseEntity<>(userService.getAccountsBasedOnRole(role), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccountsByIds(@RequestParam(value = "ids[]") Integer[] ids) {
        return new ResponseEntity<>(userService.getAccountsByIds(ids), HttpStatus.OK);
    }
}
