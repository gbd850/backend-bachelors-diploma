package edu.pollub.kidservice.model;

import jakarta.persistence.Entity;

import java.sql.Date;
import java.util.List;

@Entity
public class AttendanceList {
    private Date date;
    private List<Attendance> attendance;
}
