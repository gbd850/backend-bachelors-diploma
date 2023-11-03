package edu.pollub.dictionaryservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;

}
