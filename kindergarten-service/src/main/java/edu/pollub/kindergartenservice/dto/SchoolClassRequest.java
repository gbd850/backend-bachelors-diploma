package edu.pollub.kindergartenservice.dto;

import edu.pollub.kindergartenservice.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassRequest {
    private String name;
    private Long teacherId;
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;
    private Integer groupId;

}
