package edu.pollub.kindergartenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {
    private Date date;
    private Integer kidId;
    private Long schoolClassId;
    private boolean isPresent;
}
