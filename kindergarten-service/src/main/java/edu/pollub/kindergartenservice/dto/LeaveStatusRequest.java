package edu.pollub.kindergartenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveStatusRequest {
    private Integer id;
    private String status;
}
