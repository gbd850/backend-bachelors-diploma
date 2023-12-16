package edu.pollub.kindergartenservice.dto;

import edu.pollub.kindergartenservice.model.Facility;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private Integer employeeId;
    private Integer facilityId;
    private Date startDate;
    private Date endDate;
}
