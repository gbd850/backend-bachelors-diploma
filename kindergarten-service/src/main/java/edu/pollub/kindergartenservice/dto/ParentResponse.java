package edu.pollub.kindergartenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentResponse {
    private Integer kidId;
    private String kidFirstName;
    private String kidLastName;
    private Integer parentId;
    private String parentFirstName;
    private String parentLastName;

}
