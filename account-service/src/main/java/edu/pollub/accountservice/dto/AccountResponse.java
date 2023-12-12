package edu.pollub.accountservice.dto;

import edu.pollub.accountservice.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Integer id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
