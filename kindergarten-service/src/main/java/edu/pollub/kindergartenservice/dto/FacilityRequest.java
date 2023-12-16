package edu.pollub.kindergartenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityRequest {
    private String name;
    private Integer principalId;
}
