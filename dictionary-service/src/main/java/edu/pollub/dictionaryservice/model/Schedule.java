package edu.pollub.dictionaryservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private List<Activity> monday;
    private List<Activity> tuesday;
    private List<Activity> wednesday;
    private List<Activity> thursday;
    private List<Activity> friday;

}
