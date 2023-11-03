package edu.pollub.accountservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Length(min = 8, message = "Password must contain at least 8 characters")
    private String password;
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    private String pesel;
    private String role;
    private Date createdAt;
}
