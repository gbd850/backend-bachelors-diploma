package edu.pollub.dictionaryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id",
            referencedColumnName = "id")
    private User teacher;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id",
            referencedColumnName = "id")
    private Schedule schedule;
}
