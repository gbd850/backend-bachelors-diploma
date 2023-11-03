package edu.pollub.accountservice.service;

import edu.pollub.accountservice.dto.UserRequest;
import edu.pollub.accountservice.model.Role;
import edu.pollub.accountservice.model.User;
import edu.pollub.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(UserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .middleName(userRequest.getMiddleName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword())
                .pesel(userRequest.getPesel())
                .role(Role.PARENT.getValue())
                .createdAt(new Date(Calendar.getInstance().getTime().getTime()))
                .build();
        return userRepository.save(user);
    }
}
