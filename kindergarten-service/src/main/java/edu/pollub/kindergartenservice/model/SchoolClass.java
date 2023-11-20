package edu.pollub.kindergartenservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long teacherId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id",
            referencedColumnName = "id")
    private Schedule schedule;
}