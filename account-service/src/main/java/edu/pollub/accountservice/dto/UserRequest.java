package edu.pollub.accountservice.dto;

import edu.pollub.accountservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String pesel;
    private Role role;
    private Date createdAt;
}
