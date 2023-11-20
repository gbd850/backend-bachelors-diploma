package edu.pollub.kindergartenservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kid_id",
            referencedColumnName = "id")
    private Kid kid;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id",
            referencedColumnName = "id")
    private SchoolClass aClass;
    private boolean isPresent;
}
