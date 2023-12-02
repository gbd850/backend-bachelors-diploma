package edu.pollub.accountservice.service;

import edu.pollub.accountservice.config.JwtService;
import edu.pollub.accountservice.dto.LoginRequest;
import edu.pollub.accountservice.dto.TokenRequest;
import edu.pollub.accountservice.dto.UserRequest;
import edu.pollub.accountservice.model.Role;
import edu.pollub.accountservice.model.User;
import edu.pollub.accountservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(UserDetails user) {
        userRepository.save((User) user);
    }

    public User registerUser(UserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .middleName(userRequest.getMiddleName())
                .lastName(userRequest.getLastName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .pesel(userRequest.getPesel())
                .role(Role.valueOf(userRequest.getRole().toUpperCase()))
                .createdAt(new Date(Calendar.getInstance().getTime().getTime()))
                .build();
        createUser(user);
        return user;
    }

    public String authenticateUser(LoginRequest loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return jwtService.generateToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Boolean validateToken(TokenRequest tokenRequest) {
        String email = jwtService.extractUsername(tokenRequest.getToken());
        UserDetails userDetails = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        boolean isTokenValid = jwtService.isTokenValid(tokenRequest.getToken(), userDetails);
        if (!isTokenValid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        return true;
    }
}
