package edu.pollub.kindergartenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KidRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String pesel;
    private Date birthDate;
    private Long parentId;
    private Integer groupId;
}
